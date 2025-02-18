package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.feedback;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.EvaluationFeedbackDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.EvaluationService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationQuestion.EvaluationQuestion;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationQuestion.EvaluationQuestionService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationResponse.EvaluationResponse;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project.ProjectService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.projectGroup.ProjectGroupService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.StudentService;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ProjectGroupService projectGroupService;
    private final StudentService studentService;
    private final EvaluationService evaluationService;
    private final EvaluationQuestionService evaluationQuestionService;
    private final ProjectService projectService;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository, ProjectGroupService projectGroupService,
            StudentService studentService, EvaluationService evaluationService,
            EvaluationQuestionService evaluationQuestionService, ProjectService projectService) {
        this.feedbackRepository = feedbackRepository;
        this.projectGroupService = projectGroupService;
        this.studentService = studentService;
        this.evaluationService = evaluationService;
        this.evaluationQuestionService = evaluationQuestionService;
        this.projectService = projectService;
    }

    public Feedback addFeedback(Feedback feedback) {
        return feedbackRepository.saveAndFlush(feedback);
    }

    public Feedback convertToEntity(EvaluationFeedbackDTO feedbackDTO) {
        System.out.println("FeedbackkDTO: " + feedbackDTO);
        Feedback feedback = new Feedback();
        //feedback.setGroup(projectGroupService.findById(feedbackDTO.getProjectGroupId()));
        feedback.setRatedByStudent(studentService.findStudentById(feedbackDTO.getRatedByStudentId()));
        feedback.setRatedStudent(studentService.findStudentById(feedbackDTO.getRatedStudentId()));
        feedback.setEvaluation(evaluationService.findById(feedbackDTO.getEvaluationId()));
        feedback.setGradePercent(feedbackDTO.getGrade());
        feedback.setDateCompleted(LocalDateTime.now());
        System.out.println("FeedbackDTO PG ID: " +feedbackDTO.getProjectGroupId());
        feedback.setGroup(projectGroupService.findById(feedbackDTO.getProjectGroupId()));
        feedback.setProject(projectService.findById(feedbackDTO.getProjectId()));

        List<EvaluationResponse> evaluationResponses = feedbackDTO.getResponses().stream()
                .map(dto -> {
                    EvaluationResponse response = new EvaluationResponse();
                    response.setResponseText(dto.getResponse());
                    response.setQuestion(evaluationQuestionService.findById(dto.getQuestionId()));
                    response.setFeedback(feedback);
                    return response;
                })
                .collect(Collectors.toList());

        feedback.setResponses(evaluationResponses);

        return feedback;
    }

}
