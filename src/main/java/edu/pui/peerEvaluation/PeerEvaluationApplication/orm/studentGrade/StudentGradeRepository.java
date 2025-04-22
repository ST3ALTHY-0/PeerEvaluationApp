package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.studentGrade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityRepository;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.Student;

@Repository
public interface StudentGradeRepository extends BaseEntityRepository<StudentGrade, Integer> {

    StudentGrade findByStudentStudentIdAndProjectProjectId(Integer studentId, Integer projectId);
}