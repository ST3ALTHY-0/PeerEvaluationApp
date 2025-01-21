package edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface EvaluationResponseRepository extends JpaRepository<EvaluationResponse, Integer> {

    // @Query("SELECT er FROM evaluation_response er WHERE evaluationQuestion.evaluation_question_id = :evaluationQuestionId")
    // EvaluationResponse selectByEvaluationQuestionId(Integer evaluationQuestionId);

}
