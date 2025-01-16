package edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {

    //define any custom queries
    
    //example
    // @Query("SELECT e FROM Evaluation e WHERE e.studentId = :studentId")
    // List<Evaluation> findByStudentId(@Param("studentId") Integer studentId);
    
}
