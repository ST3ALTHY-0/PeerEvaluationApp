package edu.pui.peerEvaluation.PeerEvaluationApplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.EvaluationFeedbackDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.EvaluationFeedbackFormDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.EvaluationFormDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.EvaluationQuestionDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.ResponseDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceSCVParser.BrightSpaceCSVParser;
import edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceSCVParser.CSVData;
import edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceSCVParser.CSVDataDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceSCVParser.SaveBrightSpaceData;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.EvaluationService;
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

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/evaluation")
public class EvaluationController {

    private static final Logger logger = LoggerFactory.getLogger(EvaluationController.class);

    private final FeedbackService feedbackService;
    private final EvaluationService evaluationService;
    private final StudentService studentService;
    private final InstructorService instructorService;
    private final BrightSpaceCSVParser brightSpaceCSVParser;
    private final SaveBrightSpaceData saveBrightSpaceData;
    private final GroupCategoryService groupCategoryService;
    private final MyClassService myClassService;

    @Autowired
    public EvaluationController(
            FeedbackService feedbackService,
            EvaluationService evaluationService,
            StudentService studentService,
            InstructorService instructorService,
            BrightSpaceCSVParser brightSpaceCSVParser,
            GroupCategoryService groupCategoryService,
            MyClassService myClassService,
            SaveBrightSpaceData saveBrightSpaceData) {
            
        this.feedbackService = feedbackService;
        this.evaluationService = evaluationService;
        this.studentService = studentService;
        this.instructorService = instructorService;
        this.brightSpaceCSVParser = brightSpaceCSVParser;
        this.saveBrightSpaceData = saveBrightSpaceData;
        this.myClassService = myClassService;
        this.groupCategoryService = groupCategoryService;
        }

    @PostMapping("/submit/feedback")
    public String submitFeedback(@ModelAttribute EvaluationFeedbackFormDTO evaluationFeedbackFormDTO,
            // @RequestParam("extraResponse") ResponseDTO extraResponse,
            Model model) {
        logger.debug("Received feedback: {}", evaluationFeedbackFormDTO);

        //filter feedback to remove null entities that get added for some reason TODO: fix the null values from happening
        List<EvaluationFeedbackDTO> filteredFeedbackList = evaluationFeedbackFormDTO.getEvaluationFeedbackDTOList().stream()
                .filter(feedback -> feedback.getEvaluationId() != null && feedback.getRatedByStudentId() != null && feedback.getRatedStudentId() != null)
                .collect(Collectors.toList());

        evaluationFeedbackFormDTO.setEvaluationFeedbackDTOList(filteredFeedbackList);
        saveBrightSpaceData.saveFeedbackToDB(evaluationFeedbackFormDTO);
        model.addAttribute("message", "Feedback submitted successfully");

        return "/student/feedback/finished";
    }

@PostMapping("/submit/form")
public String createEvaluation(@RequestParam("csvFile") MultipartFile file, @ModelAttribute EvaluationFormDTO evaluationFormDTO) {
     //TODO: maybe make DTO for myClass, groupCategory, and project
    //save csv data including students, projectGroups, groupCategory, and project
    try {
        System.out.println("InstructorId: " + evaluationFormDTO.getInstructorId());
        //parse the data from the csv
        List<CSVData> csvDataList = brightSpaceCSVParser.parseDataFromCSV(file);

        //transform the parsed data into a more usable form 
        List<CSVDataDTO> csvDataDTOList = brightSpaceCSVParser.transformData(csvDataList);

        //get references to the saved Project and the Class that project belongs to
        Project thisProject = csvDataDTOList.get(0).getProject();
        MyClass myClass = myClassService.findByClassCode(evaluationFormDTO.getClassCode());


        Project project = saveBrightSpaceData.saveProjectToDB(thisProject, evaluationFormDTO.getInstructorId(), myClass);

        
        //TODO: check that gc isnt already saved in db
        // GroupCategory thisGroupCategory = new GroupCategory();
        // thisGroupCategory.setMyClass(myClass);
        // GroupCategory groupCategory = groupCategoryService.addGroupCategory(thisGroupCategory);

        //save the students and project groups they belong to in the DB
        GroupCategory groupCategory = saveBrightSpaceData.saveCSVDataToDB(csvDataDTOList, myClass, project);

        //save the evaluation to the DB
        saveBrightSpaceData.saveEvaluationToDB(evaluationFormDTO, groupCategory, project);
        
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
    return "/instructor/dashboard";
}

@GetMapping("/details")
public String evaluationsDetails(@RequestParam Integer evaluationId, Model model){
    Evaluation evaluation = evaluationService.findById(evaluationId).orElse(null);
    
    if(evaluation == null){
        model.addAttribute("errorMessage", "No evaluation found");
        return "/instructor/error"; 
    }
    //pass evaluation
    model.addAttribute("evaluation", evaluation);

    //


    return "/instructor/evaluationDetails";
}
}