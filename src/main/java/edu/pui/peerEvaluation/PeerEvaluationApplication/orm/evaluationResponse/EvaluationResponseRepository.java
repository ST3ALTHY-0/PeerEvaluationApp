package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityRepository;

import java.util.List;

public interface EvaluationResponseRepository extends BaseEntityRepository<EvaluationResponse, Integer> {
}
