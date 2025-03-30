package edu.pui.peerEvaluation.PeerEvaluationApplication.DTO;

import java.util.List;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.Evaluation;
import lombok.Data;

@Data
public class EvaluationFormDTO {
    
    private String classCode;
    private String projectName;
    private boolean enableGrading;
    private boolean useStandardForm;
    private boolean allowStudentToViewFeedback;
    private String dueDate;
    private Integer instructorId;
    private List<EvaluationQuestionDTO> evaluationQuestions;

}
