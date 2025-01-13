package edu.pui.peerEvaluation.Peerevualuationapplication.Database.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{
    
    //Write the necessary queries and functions that will be needed
    
}
