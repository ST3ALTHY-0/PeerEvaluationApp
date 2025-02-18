package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationOverride;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationOverrideRepository extends JpaRepository<EvaluationOverride, Integer> {
}