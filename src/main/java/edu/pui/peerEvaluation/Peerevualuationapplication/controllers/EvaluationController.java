package edu.pui.peerEvaluation.Peerevualuationapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;


import edu.pui.peerEvaluation.Peerevualuationapplication.DTO.EvaluationFeedbackDTO;
import edu.pui.peerEvaluation.Peerevualuationapplication.DTO.EvaluationFeedbackFormDTO;
import edu.pui.peerEvaluation.Peerevualuationapplication.DTO.EvaluationFormDTO;
import edu.pui.peerEvaluation.Peerevualuationapplication.DTO.EvaluationQuestionDTO;
import edu.pui.peerEvaluation.Peerevualuationapplication.DTO.ResponseDTO;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation.EvaluationService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationQuestion.EvaluationQuestionService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationResponse.EvaluationResponse;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationResponse.EvaluationResponseService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.feedback.Feedback;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.feedback.FeedbackService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.instructor.Instructor;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.instructor.InstructorService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.student.Student;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.student.StudentService;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/evaluation")
public class EvaluationController {

    private static final Logger logger = LoggerFactory.getLogger(EvaluationController.class);

    
    private final FeedbackService feedbackService;
    private final EvaluationQuestionService evaluationQuestionService;
    private final EvaluationResponseService evaluationResponseService;
    private final EvaluationService evaluationService;
    private final StudentService studentService;
    private final InstructorService instructorService;

    @Autowired
    public EvaluationController(FeedbackService feedbackService, 
                                EvaluationQuestionService evaluationQuestionService, 
                                EvaluationResponseService evaluationResponseService, 
                                EvaluationService evaluationService, 
                                StudentService studentService, 
                                InstructorService instructorService) {
        this.feedbackService = feedbackService;
        this.evaluationQuestionService = evaluationQuestionService;
        this.evaluationResponseService = evaluationResponseService;
        this.evaluationService = evaluationService;
        this.studentService = studentService;
        this.instructorService = instructorService;
    }

    @PostMapping("/submit/feedback")
    public String submitFeedback(@ModelAttribute EvaluationFeedbackFormDTO evaluationFeedbackFormDTO,
    // @RequestParam("extraResponse") ResponseDTO extraResponse,
            Model model) {

                logger.debug("Received feedback: {}", evaluationFeedbackFormDTO);

                List<EvaluationFeedbackDTO> evaluationFeedbackDTOList = evaluationFeedbackFormDTO.getEvaluationFeedbackDTOList();
                System.out.println("HELLOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO11");

                System.out.println(evaluationFeedbackDTOList);



        // Add attributes to the model if needed
        model.addAttribute("message", "Feedback submitted successfully");
        model.addAttribute("evaluationFeedbackDTOList", evaluationFeedbackDTOList);
        // Redirect or return a view name
        return "/student/feedback/finished";
    }

    @Transactional
    @PostMapping("/submit/form")
    public String createEvaluation(@ModelAttribute EvaluationFormDTO evaluationForm){
        //need to get class/project ids
        //need to get groupCategory BS id

        //need to get eval form info
        //how many questions? what do the questions ask
        //mandatory answer?
        //Due Date?
        //other settings?
        Student me = studentService.findStudentByEmail("monroe.luke36@gmail.com");
        Instructor in = instructorService.findInstructorByEmail("monroe.luke36@gmail.com");

        System.out.println("ssssss");
        System.out.println("Class ID: " + evaluationForm.getClassId());
        System.out.println("Project ID: " + evaluationForm.getProjectId());
        //System.out.println("Group Type: " + evaluationForm.getGroupCategory());
        System.out.println("Group Members: " + evaluationForm.getGroupMembers());
        System.out.println("Enable Grading: " + evaluationForm.isEnableGrading());
        System.out.println("Use Standard Form: " + evaluationForm.isUseStandardForm());

        if(evaluationForm.getEvaluationQuestions() != null){
        for (EvaluationQuestionDTO question : evaluationForm.getEvaluationQuestions()) {
            System.out.println("Question: " + question.getQuestionText());
            System.out.println("Is Required: " + question.isRequired());
        }
    }
        System.out.println("Due Date: " + evaluationForm.getDueDate());

        Evaluation eval = evaluationService.convertToEntity(evaluationForm, me, in);
        evaluationService.addEvaluation(eval); //causing concurrentModificationException

        System.out.println(evaluationService.findByStudentId(1));
        return "/instructor/dashboard";
    }
}