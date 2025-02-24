package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.studentGrade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentGradeService {

    private final StudentGradeRepository studentGradeRepository;

    @Autowired
    public StudentGradeService(StudentGradeRepository studentGradeRepository){
        this.studentGradeRepository = studentGradeRepository;
    }

    public StudentGrade saveStudentGrade(StudentGrade studentGrade){
        return studentGradeRepository.save(studentGrade);
    }

    public StudentGrade addStudentGrade(StudentGrade studentGrade){
        return studentGradeRepository.saveAndFlush(studentGrade);
    }
    
}
