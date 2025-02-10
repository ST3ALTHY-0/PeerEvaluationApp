package edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {

    // Find evaluations by completion status
    @Query("SELECT e FROM Evaluation e WHERE e.isComplete = :isComplete")
    List<Evaluation> findByIsCompleted(@Param("isComplete") boolean isComplete);

    @Query("SELECT evaluationQuestions FROM Evaluation e WHERE e.evaluationId = :evaluationId")
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

}
