package edu.pui.peerEvaluation.Peerevualuationapplication.DTO;

import java.util.List;

import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation.Evaluation;
import lombok.Data;

@Data
public class EvaluationFormDTO {
    private Integer groupCategoryId; //could be null
    private Integer projectId;//could be null
    private Integer classId;//could be null

    private List<Integer> groupMembers; //could be null
    private String customGroupCategoryName; //could be null
    private String customProjectName; //could be null
    private String customClassName; //could be null

    private boolean enableGrading;
    private boolean useStandardForm;
    private String dueDate;
    private List<EvaluationQuestionDTO> evaluationQuestions; //could be null

}
