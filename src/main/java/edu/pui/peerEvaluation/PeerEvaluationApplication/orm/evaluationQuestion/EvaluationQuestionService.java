package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationQuestion;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityRepository;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationResponse.EvaluationResponse;

@Service
public class EvaluationQuestionService extends BaseEntityService<EvaluationQuestion, Integer>{

    private final EvaluationQuestionRepository evaluationQuestionRepository;

    @Autowired
    public EvaluationQuestionService(EvaluationQuestionRepository evaluationQuestionRepository) {
        this.evaluationQuestionRepository = evaluationQuestionRepository;
    }

    public EvaluationQuestion addEvaluationQuestion(EvaluationQuestion evaluationQuestion) {
        return evaluationQuestionRepository.saveAndFlush(evaluationQuestion);
    }

    public Optional<EvaluationQuestion> findById(Integer evaluationQuestionId) {
        return evaluationQuestionRepository.findById(evaluationQuestionId);
    }

    @Override
    protected BaseEntityRepository<EvaluationQuestion, Integer> getRepository() {
        return evaluationQuestionRepository;
    }

}
