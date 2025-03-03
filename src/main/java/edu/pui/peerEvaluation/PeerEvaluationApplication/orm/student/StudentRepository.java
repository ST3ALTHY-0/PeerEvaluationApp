package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityRepository;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project.Project;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.projectGroup.ProjectGroup;

@Repository
public interface StudentRepository extends BaseEntityRepository<Student, Integer> {

    Optional<Student> findByStudentEmail(String studentEmail);

    Optional<Student> findByStudentEmailAndPuid(String studentEmail, String puid);

}
