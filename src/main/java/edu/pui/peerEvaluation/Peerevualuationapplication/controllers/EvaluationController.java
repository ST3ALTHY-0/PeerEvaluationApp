package edu.pui.peerEvaluation.Peerevualuationapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/feedback/submit")
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
                evaluationResponse.setResponse_text(response.getResponse());
                evaluationResponse.setFeedback(feedback);
                evalResponses.add(evaluationResponse);
                System.out.println(evaluationResponse);
            }
            // save responses to db
            evaluationResponseService.saveAllAndFlush(evalResponses);

            // set feedback
            feedback.setDate_completed(LocalDateTime.now());
            feedback.setEvaluation(evaluationService.findById(evaluationId));
            feedback.setGrade_percent(0); // set later
            feedback.setRated_by_student(studentService.findStudentById(ratingStudentId)); // prob needs changed
            feedback.setRated_student(studentService.findStudentById(studentId));
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
}