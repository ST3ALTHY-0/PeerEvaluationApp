package edu.pui.peerEvaluation.Peerevualuationapplication.Database.Class;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Class, Integer>{
    
    //Write the necessary queries and functions that will be needed
    
}
