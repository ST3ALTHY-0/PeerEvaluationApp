package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.instructor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityRepository;

@Repository
public interface InstructorRepository extends BaseEntityRepository<Instructor, Integer> {

    // Write the necessary queries and functions that will be needed
    @Query("SELECT i FROM Instructor i WHERE i.instructorEmail = :email")
    Optional<Instructor> findByEmail(String email);


    Optional<Instructor> findByInstructorEmailAndPuid(String email, String puid);

}
