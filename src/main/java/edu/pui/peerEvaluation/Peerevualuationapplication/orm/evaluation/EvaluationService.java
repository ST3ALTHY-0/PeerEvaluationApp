package edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;

    @Autowired
    public EvaluationService(EvaluationRepository evaluationRepository){
        this.evaluationRepository = evaluationRepository;
    }

    public Evaluation addEvaluation(Evaluation evaluation){
        return evaluationRepository.saveAndFlush(evaluation);

    }
    
}
