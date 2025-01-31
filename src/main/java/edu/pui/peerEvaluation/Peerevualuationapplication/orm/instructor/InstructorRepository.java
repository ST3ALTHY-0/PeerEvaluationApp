package edu.pui.peerEvaluation.Peerevualuationapplication.orm.instructor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {

    // Write the necessary queries and functions that will be needed

}
