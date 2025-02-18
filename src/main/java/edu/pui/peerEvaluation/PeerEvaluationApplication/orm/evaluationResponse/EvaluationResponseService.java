package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationResponseService {

    private final EvaluationResponseRepository evaluationResponseRepository;

    @Autowired
    public EvaluationResponseService(EvaluationResponseRepository evaluationResponseRepository) {
        this.evaluationResponseRepository = evaluationResponseRepository;
    }

    public EvaluationResponse addEvaluationResponse(EvaluationResponse evaluationResponse) {
        return evaluationResponseRepository.saveAndFlush(evaluationResponse);

    }

    public List<EvaluationResponse> saveAllAndFlush(List<EvaluationResponse> responses) {
        return evaluationResponseRepository.saveAllAndFlush(responses);
    }

}
