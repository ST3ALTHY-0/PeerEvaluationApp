package edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationResponseService {

    private final EvaluationResponseRepository evaluationResponseRepository;

    @Autowired
    public EvaluationResponseService(EvaluationResponseRepository evaluationResponseRepository){
        this.evaluationResponseRepository = evaluationResponseRepository;
    }

    public EvaluationResponse addEvaluationResponse(EvaluationResponse evaluationResponse){
        return evaluationResponseRepository.saveAndFlush(evaluationResponse);

    }
    
}
