package edu.pui.peerEvaluation.Peerevualuationapplication.Database.Grade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer>{
    
    //Write the necessary queries and functions that will be needed 
    
}
