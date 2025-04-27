package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.feedback;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.EvaluationFeedbackDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityRepository;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.EvaluationService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationQuestion.EvaluationQuestion;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationQuestion.EvaluationQuestionService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationResponse.EvaluationResponse;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project.ProjectService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.projectGroup.ProjectGroupService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.StudentService;

@Service
public class FeedbackService extends BaseEntityService<Feedback, Integer>{

    private final FeedbackRepository feedbackRepository;


    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;

    }

    public boolean existsByEvaluationIdAndRatedByStudentIdAndRatedStudentId(int evaluationId, int ratedByStudentId, int ratedStudentId) {
        return feedbackRepository.existsByEvaluationEvaluationIdAndRatedByStudentStudentIdAndRatedStudentStudentId(evaluationId, ratedByStudentId, ratedStudentId);
    }

    @Override
    protected BaseEntityRepository<Feedback, Integer> getRepository() {
        return feedbackRepository;
    }


}
