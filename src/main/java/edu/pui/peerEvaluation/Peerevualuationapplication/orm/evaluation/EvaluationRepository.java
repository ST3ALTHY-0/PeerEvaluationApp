package edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {



    // example
    // @Query("SELECT e FROM Evaluation e WHERE e.studentId = :studentId")
    // List<Evaluation> findByStudentId(@Param("studentId") Integer studentId);

    // Find evaluations by instructor ID
    @Query("SELECT e FROM Evaluation e WHERE e.instructor.instructorId = :instructorId")
    List<Evaluation> findByInstructorId(@Param("instructorId") int instructorId);

    // Find evaluations by project ID
    @Query("SELECT e FROM Evaluation e WHERE e.project.projectId = :projectId")
    List<Evaluation> findByProjectId(@Param("projectId") int projectId);

    // Find evaluations by completion status
    @Query("SELECT e FROM Evaluation e WHERE e.isComplete = :isComplete")
    List<Evaluation> findByIsCompleted(@Param("isComplete") boolean isComplete);

    // Optional: Find evaluations by instructor and completion status
    @Query("SELECT e FROM Evaluation e WHERE e.instructor.instructorId = :instructorId AND e.isComplete = :isComplete")
    List<Evaluation> findByInstructorIdAndIsCompleted(@Param("instructorId") int instructorId,
            @Param("isComplete") boolean isComplete);

    @Query("SELECT evaluationQuestions FROM Evaluation e WHERE e.evaluationId = :evaluationId")
    List<Evaluation> findEvaluationQuestionsById(Integer evaluationId);

    @Query("SELECT DISTINCT e FROM Evaluation e " +
           "JOIN e.projectGroups pg " +
           "JOIN pg.students s " +
           "WHERE s.id = :studentId")
    List<Evaluation> findEvaluationsByStudentId(@Param("studentId") Integer studentId);

    @Query("SELECT DISTINCT e FROM Evaluation e " +
    "JOIN e.projectGroups pg " +
    "JOIN pg.students s " +
    "LEFT JOIN Feedback f ON f.evaluation = e AND f.ratedByStudent.id = :studentId " +
    "WHERE s.studentId = :studentId AND f.feedbackId IS NULL")
List<Evaluation> findAllByStudentIdAndNoFeedback(@Param("studentId") int studentId);

@Query("SELECT DISTINCT e FROM Evaluation e " +
"JOIN e.projectGroups pg " +
"JOIN pg.students s " +
"LEFT JOIN Feedback f ON f.evaluation = e AND f.ratedByStudent.id = :studentId " +
"WHERE s.id = :studentId AND f.feedbackId IS NOT NULL")
List<Evaluation> findAllByStudentIdWithFeedback(@Param("studentId") int studentId);


}
