package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationQuestion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityRepository;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationResponse.EvaluationResponse;

import java.util.List;

public interface EvaluationQuestionRepository extends BaseEntityRepository<EvaluationQuestion, Integer> {

    }
