package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationOverride;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityRepository;

@Repository
public interface EvaluationOverrideRepository extends BaseEntityRepository<EvaluationOverride, Integer> {
    Optional<EvaluationOverride> findByStudentStudentIdAndEvaluationEvaluationId(Integer studentId, Integer evaluationId);
    List<EvaluationOverride> findByEvaluationEvaluationId(Integer evaluationId);

}