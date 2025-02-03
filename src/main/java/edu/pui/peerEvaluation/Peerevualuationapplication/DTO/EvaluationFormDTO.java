package edu.pui.peerEvaluation.Peerevualuationapplication.DTO;

import java.util.List;

import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation.Evaluation;
import lombok.Data;

@Data
public class EvaluationFormDTO {
    private Integer classId;
    private Integer projectId;
    private String groupCategory;
    private List<Integer> groupMembers;
    private boolean enableGrading;
    private boolean useStandardForm;
    private String dueDate;
    private List<EvaluationQuestionDTO> evaluationQuestions;

}
