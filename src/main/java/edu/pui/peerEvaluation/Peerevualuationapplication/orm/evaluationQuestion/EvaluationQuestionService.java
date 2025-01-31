package edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationQuestion;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationResponse.EvaluationResponse;

@Service
public class EvaluationQuestionService {

    private final EvaluationQuestionRepository evaluationQuestionRepository;

    @Autowired
    public EvaluationQuestionService(EvaluationQuestionRepository evaluationQuestionRepository) {
        this.evaluationQuestionRepository = evaluationQuestionRepository;
    }

    public EvaluationQuestion addEvaluationQuestion(EvaluationQuestion evaluationQuestion) {
        return evaluationQuestionRepository.saveAndFlush(evaluationQuestion);
    }

    public EvaluationQuestion findById(Integer evaluationQuestionId) {
        return evaluationQuestionRepository.findById(evaluationQuestionId).orElse(null);
    }

}
