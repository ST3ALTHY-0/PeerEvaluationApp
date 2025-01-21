package edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;

    @Autowired
    public EvaluationService(EvaluationRepository evaluationRepository){
        this.evaluationRepository = evaluationRepository;
    }

    public Evaluation addEvaluation(Evaluation evaluation){
        return evaluationRepository.saveAndFlush(evaluation);

    }

    public List<Evaluation> findByStudentId(Integer studentId){
        return evaluationRepository.findByStudentId(studentId);
        }

    public List<Evaluation> findAll() {
        return evaluationRepository.findAll();
    }

    public Evaluation findById(int evaluationId) {
        return evaluationRepository.findById(evaluationId).orElse(null);
    }
    
}
