package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationOverride;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationOverrideService {

    private final EvaluationOverrideRepository evaluationOverrideRepository;

    @Autowired
    public EvaluationOverrideService(EvaluationOverrideRepository evaluationOverrideRepository){
        this.evaluationOverrideRepository = evaluationOverrideRepository;
    }

    public EvaluationOverride addEvaluationOverride(EvaluationOverride evaluationOverride){
        return evaluationOverrideRepository.save(evaluationOverride);
    }

    //need a funtion to find evaluation overrides for a student
    
}
