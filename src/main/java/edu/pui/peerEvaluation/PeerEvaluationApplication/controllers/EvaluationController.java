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
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.projectGroup.ProjectGroup;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.Student;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.StudentService;

import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    public EvaluationController(
            FeedbackService feedbackService,
            EvaluationService evaluationService,
            StudentService studentService,
            InstructorService instructorService,
            BrightSpaceCSVParser brightSpaceCSVParser) {
        this.feedbackService = feedbackService;
        this.evaluationService = evaluationService;
        this.studentService = studentService;
        this.instructorService = instructorService;
        this.brightSpaceCSVParser = brightSpaceCSVParser;
    }

    @PostMapping("/submit/feedback")
    public String submitFeedback(@ModelAttribute EvaluationFeedbackFormDTO evaluationFeedbackFormDTO,
            // @RequestParam("extraResponse") ResponseDTO extraResponse,
            Model model) {
        logger.debug("Received feedback: {}", evaluationFeedbackFormDTO);

        List<EvaluationFeedbackDTO> filteredFeedbackList = evaluationFeedbackFormDTO.getEvaluationFeedbackDTOList().stream()
                .filter(feedback -> feedback.getEvaluationId() != null && feedback.getRatedByStudentId() != null && feedback.getRatedStudentId() != null)
                .collect(Collectors.toList());

        evaluationFeedbackFormDTO.setEvaluationFeedbackDTOList(filteredFeedbackList);
        for(EvaluationFeedbackDTO feedbackDTO : evaluationFeedbackFormDTO.getEvaluationFeedbackDTOList())
        {
            System.out.println("GroupID: " + feedbackDTO.getProjectGroupId());
            Feedback feedback = feedbackService.convertToEntity(feedbackDTO);
            feedbackService.addFeedback(feedback);
        }
        model.addAttribute("message", "Feedback submitted successfully");

        // Redirect or return a view name
        return "/student/feedback/finished";
    }

  // @Transactional
@PostMapping("/submit/form")
public String createEvaluation(@RequestParam("csvFile") MultipartFile file, @ModelAttribute EvaluationFormDTO evaluationFormDTO) {

    try {
        List<CSVData> csvDataList = brightSpaceCSVParser.parseDataFromCSV(file);

        List<CSVDataDTO> csvDataDTOList = brightSpaceCSVParser.transformData(csvDataList);

        
        //System.out.println(csvData);
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }



    return "/instructor/dashboard";
}
}