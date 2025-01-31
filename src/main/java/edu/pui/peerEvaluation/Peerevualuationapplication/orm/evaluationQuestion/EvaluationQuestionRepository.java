package edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationQuestion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationResponse.EvaluationResponse;

import java.util.List;

public interface EvaluationQuestionRepository extends JpaRepository<EvaluationQuestion, Integer> {

    //select questions where eval id
    @Query("SELECT eq FROM EvaluationQuestion eq WHERE eq.evaluation.evaluationId = :evaluationId")
        List<EvaluationQuestion> selectQuestionsByEvaluationId(Integer evaluationId);

        // @Query("SELECT response FROM EvaluationQuestion eq WHERE evaluation_question_id = :evaluationQuestionId")
        // EvaluationResponse selectResponseById(Integer evaluationQuestionId);
    }
