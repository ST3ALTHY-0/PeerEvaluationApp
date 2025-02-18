package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.myClass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyClassRepository extends JpaRepository<MyClass, Integer> {

    // Write the necessary queries and functions that will be needed

}
