package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityRepository;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityService;

@Service
public class EvaluationResponseService extends BaseEntityService<EvaluationResponse, Integer>{

    private final EvaluationResponseRepository evaluationResponseRepository;

    @Autowired
    public EvaluationResponseService(EvaluationResponseRepository evaluationResponseRepository) {
        this.evaluationResponseRepository = evaluationResponseRepository;
    }

    @Override
    protected BaseEntityRepository<EvaluationResponse, Integer> getRepository() {
        return evaluationResponseRepository;
    }

}
