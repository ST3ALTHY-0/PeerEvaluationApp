package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EvaluationRestController{

    private final EvaluationService evaluationService;

    @Autowired
    public EvaluationRestController(EvaluationService evaluationService){
        this.evaluationService = evaluationService;
    }

    @GetMapping("/projectGroup/{projectGroupId}")
    public Evaluation getGroup(@PathVariable Integer evaluationId) {
        return evaluationService.findById(evaluationId);
    }
    
}
