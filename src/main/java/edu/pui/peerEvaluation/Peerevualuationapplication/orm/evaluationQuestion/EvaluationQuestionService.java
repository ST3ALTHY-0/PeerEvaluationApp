package edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationQuestion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationQuestionService {

    private final EvaluationQuestionRepository evaluationQuestionRepository;

    @Autowired
    public EvaluationQuestionService(EvaluationQuestionRepository evaluationQuestionRepository){
        this.evaluationQuestionRepository = evaluationQuestionRepository;
    }

    public EvaluationQuestion addEvaluationQuestion(EvaluationQuestion evaluationQuestion){
        return evaluationQuestionRepository.saveAndFlush(evaluationQuestion);

    }
    
}
