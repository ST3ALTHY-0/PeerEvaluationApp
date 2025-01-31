package edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {

    // define any custom queries

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

    // Optional: Find evaluations by a student
    @Query("SELECT e FROM Evaluation e JOIN e.students s WHERE s.studentId = :studentId")
    List<Evaluation> findByStudentId(Integer studentId);

    @Query("SELECT evaluationQuestions FROM Evaluation e WHERE e.evaluationId = :evaluationId")
    List<Evaluation> findEvaluationQuestionsById(Integer evaluationId);

}
