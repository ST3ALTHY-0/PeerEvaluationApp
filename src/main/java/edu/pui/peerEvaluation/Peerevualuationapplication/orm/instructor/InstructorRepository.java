package edu.pui.peerEvaluation.Peerevualuationapplication.orm.instructor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {

    // Write the necessary queries and functions that will be needed
     @Query("SELECT i FROM Instructor i WHERE i.instructor_email = :email")
    Optional<Instructor> findByEmail(String email);

}
