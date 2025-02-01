package edu.pui.peerEvaluation.Peerevualuationapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.pui.peerEvaluation.Peerevualuationapplication.DTO.EvaluationForm;
import edu.pui.peerEvaluation.Peerevualuationapplication.DTO.EvaluationQuestionDTO;
import edu.pui.peerEvaluation.Peerevualuationapplication.DTO.Response;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation.EvaluationService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationQuestion.EvaluationQuestionService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationResponse.EvaluationResponse;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationResponse.EvaluationResponseService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.feedback.Feedback;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.feedback.FeedbackService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.student.StudentService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/evaluation")
public class EvaluationController {

    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private EvaluationQuestionService evaluationQuestionService;

    @Autowired
    private EvaluationResponseService evaluationResponseService;

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private StudentService studentService;

    @PostMapping("/submit/feedback")
    public String submitFeedback(@RequestParam("evaluationId") Integer evaluationId,
            @RequestParam("evaluatorId") Integer ratingStudentId,
            @RequestParam("ratedStudentId") Integer ratedStudentId,
            @RequestParam("projectGroupId") Integer projectGroupId,
            @ModelAttribute("responses") Map<Integer, List<Response>> responses,
            Model model) {
        // Process the feedback
        for (Map.Entry<Integer, List<Response>> entry : responses.entrySet()) {
            Integer studentId = entry.getKey();
            List<Response> studentResponses = entry.getValue();

            // create obj
            Feedback feedback = new Feedback();
            List<EvaluationResponse> evalResponses = new ArrayList<EvaluationResponse>();

            // Process each student's responses
            for (Response response : studentResponses) {
                // Save each response and add to list
                EvaluationResponse evaluationResponse = new EvaluationResponse();
                evaluationResponse.setQuestion(evaluationQuestionService.findById(response.getQuestionId()));
                evaluationResponse.setResponseText(response.getResponse());
                evaluationResponse.setFeedback(feedback);
                evalResponses.add(evaluationResponse);
                System.out.println(evaluationResponse);
            }
            // save responses to db
            evaluationResponseService.saveAllAndFlush(evalResponses);

            // set feedback
            feedback.setDateCompleted(LocalDateTime.now());
            feedback.setEvaluation(evaluationService.findById(evaluationId));
            feedback.setGradePercent(0); // set later
            feedback.setRatedByStudent(studentService.findStudentById(ratingStudentId)); // prob needs changed
            feedback.setRatedStudent(studentService.findStudentById(studentId));
            feedback.setResponses(evalResponses);
            System.out.println(feedback);

            // feedback.setGroup(); //set group, might need to pass project group from
            // thymeleaf

            // save feedback to db
            feedbackService.addFeedback(feedback);
        }

        // Add attributes to the model if needed
        model.addAttribute("message", "Feedback submitted successfully");

        // Redirect or return a view name
        return "/student/feedback/finished";
    }

    @PostMapping("/submit/form")
    public String createEvaluation(@ModelAttribute EvaluationForm evaluationForm){
        //need to get class/project ids
        //need to get groupCategory BS id

        //need to get eval form info
        //how many questions? what do the questions ask
        //mandatory answer?
        //Due Date?
        //other settings?
        System.out.println("Class ID: " + evaluationForm.getClassId());
        System.out.println("Project ID: " + evaluationForm.getProjectId());
        System.out.println("Group Type: " + evaluationForm.getGroupType());
        System.out.println("Group Members: " + evaluationForm.getGroupMembers());
        System.out.println("Enable Grading: " + evaluationForm.isEnableGrading());
        System.out.println("Use Standard Form: " + evaluationForm.isUseStandardForm());
        // for(EvaluationQuestionDTO question : evaluationForm.getEvaluationQuestions()){
        //     System.out.println("Question: " + question.getQuestionText());
        // }
        System.out.println("Questions: " + evaluationForm.getEvaluationQuestions());

        System.out.println("Due Date: " + evaluationForm.getDueDate());

        return "/instructor/dashboard";
    }
}