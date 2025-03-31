package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.studentGrade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityRepository;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.Student;

@Service
public class StudentGradeService extends BaseEntityService<StudentGrade, Integer>{

    private final StudentGradeRepository studentGradeRepository;

    @Autowired
    public StudentGradeService(StudentGradeRepository studentGradeRepository){
        this.studentGradeRepository = studentGradeRepository;
    }


    @Override
    protected BaseEntityRepository<StudentGrade, Integer> getRepository() {
        return studentGradeRepository;
    }
    
}
