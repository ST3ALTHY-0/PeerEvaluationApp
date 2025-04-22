package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationOverride;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityRepository;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.EvaluationService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.Student;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.StudentService;

@Service
public class EvaluationOverrideService extends BaseEntityService<EvaluationOverride, Integer>{

    private final EvaluationOverrideRepository evaluationOverrideRepository;

    private final StudentService studentService;
    private final EvaluationService evaluationService;

    @Autowired
    public EvaluationOverrideService(EvaluationOverrideRepository evaluationOverrideRepository, 
                                     StudentService studentService, 
                                     EvaluationService evaluationService) {
        this.evaluationOverrideRepository = evaluationOverrideRepository;
        this.studentService = studentService;
        this.evaluationService = evaluationService;
    }


    @Override
    protected BaseEntityRepository<EvaluationOverride, Integer> getRepository() {
        return evaluationOverrideRepository;
    }

    public EvaluationOverride extendStudentDeadline(Integer studentId, Integer evaluationId, LocalDateTime extensionDeadline) throws Exception{

        Optional<Evaluation> evaluation = evaluationService.findById(evaluationId);
        Optional<Student> student = studentService.findById(studentId);

        if (evaluation.isEmpty()) {
            throw new Exception("Evaluation with ID " + evaluationId + " not found.");
        }

        if (student.isEmpty()) {
            throw new Exception("Student with ID " + studentId + " not found.");
        }

        EvaluationOverride evaluationOverride = new EvaluationOverride();
        evaluationOverride.setEvaluation(evaluation.get());
        evaluationOverride.setStudent(student.get());
        evaluationOverride.setExtendedDeadline(extensionDeadline);


        Optional<EvaluationOverride> existingOverride = evaluationOverrideRepository
            .findByStudentStudentIdAndEvaluationEvaluationId(studentId, evaluationId);

        if (existingOverride.isPresent()) {
            evaluationOverride.setEvaluationOverrideId(existingOverride.get().getEvaluationOverrideId());
        }

        return evaluationOverrideRepository.save(evaluationOverride);

    }
    
}
