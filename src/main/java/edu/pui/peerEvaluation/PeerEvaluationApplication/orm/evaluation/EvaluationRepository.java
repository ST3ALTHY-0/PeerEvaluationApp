package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityRepository;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.feedback.Feedback;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.Student;

import java.util.List;

public interface EvaluationRepository extends BaseEntityRepository<Evaluation, Integer> {

        // Find evaluations by completion status
        List<Evaluation> findByIsComplete(boolean isComplete);

        List<Evaluation> findEvaluationQuestionsByEvaluationId(Integer evaluationId);

        List<Evaluation> findDistinctByGroupCategory_ProjectGroups_Students_StudentId(Integer studentId);

        List<Evaluation> findDistinctByGroupCategory_ProjectGroups_Students_StudentIdAndFeedbacks_FeedbackIdIsNull(
                        Integer studentId);

        List<Evaluation> findDistinctByGroupCategory_ProjectGroups_Students_StudentIdAndFeedbacks_FeedbackIdIsNotNull(
                        Integer studentId);

        @EntityGraph(attributePaths = { "groupCategory", "groupCategory.projectGroups",
                        "groupCategory.projectGroups.students" })
        @Query("""
                            SELECT DISTINCT e FROM Evaluation e
                            JOIN e.groupCategory gc
                            JOIN gc.projectGroups pg
                            JOIN pg.students s
                            LEFT JOIN e.feedbacks f
                            WHERE s.studentId = :studentId
                            AND (f IS NULL OR f.ratedByStudent.studentId <> :studentId)
                        """)
        List<Evaluation> findEvaluationsWithoutStudentFeedback(@Param("studentId") Integer studentId);

        List<Evaluation> findByProject_Instructor_InstructorId(
                Integer instructorId);

        
        @Query("SELECT COUNT(s) FROM Evaluation e JOIN e.groupCategory gc JOIN gc.projectGroups pg JOIN pg.students s WHERE e.evaluationId = :evaluationId")
        Integer countStudentsAssignedToEvaluation(@Param("evaluationId") Integer evaluationId);


        @Query("SELECT COUNT(DISTINCT f.ratedByStudent) FROM Feedback f WHERE f.evaluation.evaluationId = :evaluationId")
        Integer countStudentsWhoSubmittedFeedback(@Param("evaluationId") Integer evaluationId);

        
        @Query("SELECT s FROM Evaluation e JOIN e.groupCategory gc JOIN gc.projectGroups pg JOIN pg.students s WHERE e.evaluationId = :evaluationId")
    List<Student> findStudentsAssignedToEvaluation(@Param("evaluationId") Integer evaluationId);

    @Query("SELECT f FROM Feedback f WHERE f.evaluation.evaluationId = :evaluationId AND f.ratedStudent.studentId = :studentId")
    List<Feedback> findFeedbacksForStudentInEvaluation(@Param("evaluationId") Integer evaluationId, @Param("studentId") Integer studentId);

    @Query("SELECT f FROM Feedback f WHERE f.evaluation.evaluationId = :evaluationId AND f.ratedByStudent.studentId = :studentId")
    List<Feedback> findFeedbacksByStudentInEvaluation(@Param("evaluationId") Integer evaluationId, @Param("studentId") Integer studentId);

    @Query("SELECT COUNT(f) > 0 FROM Feedback f WHERE f.evaluation.evaluationId = :evaluationId AND f.ratedByStudent.studentId = :studentId")
    boolean isEvaluationCompletedByStudent(@Param("studentId") Integer studentId, @Param("evaluationId") Integer evaluationId);
}

