package edu.pui.peerEvaluation.PeerEvaluationApplication.DTO;

import java.util.List;

import lombok.Data;

@Data
public class EvaluationFeedbackFormDTO {

    private List<EvaluationFeedbackDTO> evaluationFeedbackDTOList;
    
}