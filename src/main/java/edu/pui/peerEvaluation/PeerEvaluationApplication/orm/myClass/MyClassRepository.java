package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.myClass;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.Evaluation;

@Repository
public interface MyClassRepository extends JpaRepository<MyClass, Integer> {

    // Write the necessary queries and functions that will be needed

    @Query("SELECT c FROM MyClass c WHERE c.classCode = :classCode")
    Optional<MyClass> findByClassCode(@Param("classCode") String classCode);

}
