package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityRepository;

import java.util.List;

public interface EvaluationRepository extends BaseEntityRepository<Evaluation, Integer> {

        // Find evaluations by completion status
        List<Evaluation> findByIsCompleted(boolean isComplete);

        List<Evaluation> findEvaluationQuestionsByEvaluationId(Integer evaluationId);

        List<Evaluation> findDistinctByGroupCategories_ProjectGroups_Students_StudentId(Integer studentId);

        List<Evaluation> findDistinctByGroupCategories_ProjectGroups_Students_StudentIdAndFeedbacks_FeedbackIdIsNull(Integer studentId);

        List<Evaluation> findDistinctByGroupCategories_ProjectGroups_Students_StudentIdAndFeedbacks_FeedbackIdIsNotNull(Integer studentId);

}

/*
 *     @Query("SELECT evaluationQuestions FROM Evaluation e WHERE e.evaluationId = :evaluationId")
    List<Evaluation> findEvaluationQuestionsById(Integer evaluationId);

    @Query("SELECT DISTINCT e FROM Evaluation e " +
            "JOIN e.groupCategories gc " +
            "JOIN gc.projectGroups pg " +
            "JOIN pg.students s " +
            "WHERE s.studentId = :studentId")
    List<Evaluation> findEvaluationsByStudentId(@Param("studentId") Integer studentId);

    @Query("SELECT DISTINCT e FROM Evaluation e " +
            "JOIN e.groupCategories gc " +
            "JOIN gc.projectGroups pg " +
            "JOIN pg.students s " +
            "LEFT JOIN Feedback f ON f.evaluation = e AND f.ratedByStudent.studentId = :studentId " +
            "WHERE s.studentId = :studentId AND f.feedbackId IS NULL")
    List<Evaluation> findAllByStudentIdAndNoFeedback(@Param("studentId") int studentId);

    @Query("SELECT DISTINCT e FROM Evaluation e " +
            "JOIN e.groupCategories gc " +
            "JOIN gc.projectGroups pg " +
            "JOIN pg.students s " +
            "LEFT JOIN Feedback f ON f.evaluation = e AND f.ratedByStudent.studentId = :studentId " +
            "WHERE s.studentId = :studentId AND f.feedbackId IS NOT NULL")
    List<Evaluation> findAllByStudentIdWithFeedback(@Param("studentId") int studentId);
 */
