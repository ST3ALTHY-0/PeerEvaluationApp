package edu.pui.peerEvaluation.Peerevualuationapplication.DTO;

import java.util.List;

import lombok.Data;

@Data
public class EvaluationForm {
    private Long classId;
    private Long projectId;
    private String groupType;
    private List<Long> groupMembers;
    private boolean enableGrading;
    private boolean useStandardForm;
    private String dueDate;
    private List<EvaluationQuestionDTO> evaluationQuestions;
}
