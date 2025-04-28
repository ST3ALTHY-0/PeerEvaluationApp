package edu.pui.peerEvaluation.PeerEvaluationApplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.EvaluationFeedbackDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.EvaluationFeedbackFormDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.EvaluationFormDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.EvaluationQuestionDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.ResponseDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.sendResponseDataToClientDTO.EvaluationResponseDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.sendResponseDataToClientDTO.StudentDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceSCVParser.BrightSpaceCSVParser;
import edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceSCVParser.CSVData;
import edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceSCVParser.CSVDataDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.EvaluationService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationOverride.EvaluationOverride;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationOverride.EvaluationOverrideService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationQuestion.EvaluationQuestionService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationResponse.EvaluationResponse;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationResponse.EvaluationResponseService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.feedback.Feedback;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.feedback.FeedbackService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.groupCategory.GroupCategory;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.groupCategory.GroupCategoryService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.instructor.Instructor;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.instructor.InstructorService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.myClass.MyClass;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.myClass.MyClassService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project.Project;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.projectGroup.ProjectGroup;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.Student;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.StudentService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.saveDataToDB.SaveData;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/evaluation")
public class EvaluationController {

    private StudentDTO convertToStudentDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId(student.getStudentId());
        studentDTO.setStudentName(student.getStudentName());
        studentDTO.setStudentEmail(student.getStudentEmail());
        // Add other necessary fields here
        return studentDTO;
    }

    private static final Logger logger = LoggerFactory.getLogger(EvaluationController.class);

    private final FeedbackService feedbackService;
    private final EvaluationService evaluationService;
    private final BrightSpaceCSVParser brightSpaceCSVParser;
    private final SaveData saveData;
    private final MyClassService myClassService;
    private final EvaluationOverrideService evaluationOverrideService;

    @Autowired
    public EvaluationController(
            FeedbackService feedbackService,
            EvaluationService evaluationService,
            BrightSpaceCSVParser brightSpaceCSVParser,
            MyClassService myClassService,
            SaveData saveData,
            EvaluationOverrideService evaluationOverrideService) {
            
        this.feedbackService = feedbackService;
        this.evaluationService = evaluationService;
        this.brightSpaceCSVParser = brightSpaceCSVParser;
        this.saveData = saveData;
        this.myClassService = myClassService;
        this.evaluationOverrideService = evaluationOverrideService; 
        }

    @PostMapping("/submit/feedback")
    public String submitFeedback(@ModelAttribute EvaluationFeedbackFormDTO evaluationFeedbackFormDTO,
            Model model) {
        logger.debug("Received feedback: {}", evaluationFeedbackFormDTO);

        // Filter feedback to remove null entities
        List<EvaluationFeedbackDTO> filteredFeedbackList = evaluationFeedbackFormDTO.getEvaluationFeedbackDTOList().stream()
                .filter(feedback -> feedback.getEvaluationId() != null && feedback.getRatedByStudentId() != null && feedback.getRatedStudentId() != null)
                .collect(Collectors.toList());

        evaluationFeedbackFormDTO.setEvaluationFeedbackDTOList(filteredFeedbackList);

        // Check for duplicate feedback
        for (EvaluationFeedbackDTO feedback : filteredFeedbackList) {
            boolean exists = feedbackService.existsByEvaluationIdAndRatedByStudentIdAndRatedStudentId(
                    feedback.getEvaluationId(), feedback.getRatedByStudentId(), feedback.getRatedStudentId());
            if (exists) {
                model.addAttribute("errorMessage", "Duplicate feedback detected. Submission aborted.");
                return "/student/error";
            }
        }

        saveData.saveFeedbackToDB(evaluationFeedbackFormDTO);
        model.addAttribute("message", "Feedback submitted successfully");

        return "student/feedback/finished";
    }

@PostMapping("/submit/form")
public String createEvaluation(@RequestParam("csvFile") MultipartFile file, @ModelAttribute EvaluationFormDTO evaluationFormDTO) {
    //save csv data including students, projectGroups, groupCategory, and project
    try {
        List<CSVData> csvDataList = brightSpaceCSVParser.parseDataFromCSV(file);

        //transform the parsed data into a more usable form 
        List<CSVDataDTO> csvDataDTOList = brightSpaceCSVParser.transformData(csvDataList);

        //get references to the saved Project and the Class that project belongs to
        Project thisProject = csvDataDTOList.get(0).getProject();
        MyClass myClass = myClassService.findByClassCodeOrCreate(evaluationFormDTO.getClassCode());
        Project project = saveData.saveProjectToDB(thisProject, evaluationFormDTO.getInstructorId(), myClass);

        //save the students and project groups they belong to in the DB
        GroupCategory groupCategory = saveData.saveCSVDataToDB(csvDataDTOList, myClass, project);

        saveData.saveEvaluationToDB(evaluationFormDTO, groupCategory, project);
        
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
    return "instructor/dashboard";
}



@PostMapping("/updateDueDate")
public ResponseEntity<String> updateDueDate(@RequestBody Map<String, String> requestData) {
    try {
        String dueDateString = requestData.get("dueDate");
        LocalDateTime dueDate = LocalDateTime.parse(dueDateString + "T00:00:00");
        Integer evaluationId = Integer.parseInt(requestData.get("evaluationId"));

        // Log the received data
        logger.info("Received dueDate: {}, evaluationId: {}", dueDate, evaluationId);

        // Assuming you have a method to update the due date in the evaluation service
        Evaluation evaluation = evaluationService.updateDueDate(evaluationId, dueDate);
        return ResponseEntity.ok("Due date updated successfully.");
    } catch (Exception e) {
        logger.error("Error updating due date: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update due date.");
    }
}

@PostMapping("/extendDeadline")
public ResponseEntity<String> extendDeadline(@RequestBody Map<String, String> requestData) {
    Integer studentId = Integer.valueOf(requestData.get("studentId"));
    Integer evaluationId = Integer.valueOf(requestData.get("evaluationId"));
    LocalDateTime newDeadline = LocalDateTime.parse(requestData.get("newDeadline") + "T00:00:00"); //2025-05-10

    try {
        evaluationOverrideService.extendStudentDeadline(studentId, evaluationId, newDeadline);
        return ResponseEntity.ok("Deadline extended successfully.");

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to extend deadline.");
    }
}

@PostMapping("/getResponses")
public ResponseEntity<?> getEvaluationResponses(@RequestBody Map<String, Integer> requestData) {
    try {
        Integer studentId = requestData.get("studentId"); //student whom we are looking for responses to
        Integer evaluationId = requestData.get("evaluationId"); //evaluation we are looking at for those responses

        // Fetch evaluation responses for the given evaluation and student
        List<EvaluationResponse> evaluationResponses = evaluationService.getEvaluationResponses(evaluationId);

        //Collect all the responses for the student that was passed.
        //We collect them all in a DTO that contains the question, response and student info
        List<EvaluationResponseDTO> filteredResponses = evaluationResponses.stream()
            .filter(response -> response.getFeedback() != null &&
                        response.getFeedback().getRatedStudent().getStudentId().equals(studentId))
            .map(response -> {
                EvaluationResponseDTO dto = new EvaluationResponseDTO();
                dto.setResponseId(response.getResponseId());
                dto.setResponseText(response.getResponseText());
                dto.setQuestionText(response.getQuestion().getQuestionText());
                dto.setStudentWhoReceivedFeedback(convertToStudentDTO(response.getFeedback().getRatedStudent()));
                dto.setStudentWhoGaveFeedback(convertToStudentDTO(response.getFeedback().getRatedByStudent()));
                return dto;
            })
            .collect(Collectors.toList());

        // Prepare the response data
        Map<String, Object> responseData = Map.of("evaluationResponses", filteredResponses);

        return ResponseEntity.ok(responseData);
    } catch (Exception e) {
        logger.error("Error fetching evaluation responses: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch evaluation responses.");
    }
}

}