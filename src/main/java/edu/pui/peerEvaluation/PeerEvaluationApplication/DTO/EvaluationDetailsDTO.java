package edu.pui.peerEvaluation.PeerEvaluationApplication.DTO;


import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.Evaluation;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class EvaluationDetailsDTO {


    private Evaluation evaluation;


    private Integer responsesReceived;


    private Integer totalRespondents;


}
