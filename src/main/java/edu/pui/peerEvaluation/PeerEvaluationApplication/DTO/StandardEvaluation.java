package edu.pui.peerEvaluation.PeerEvaluationApplication.DTO;

import java.util.ArrayList;
import java.util.List;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationQuestion.EvaluationQuestion;
import lombok.Data;

@Data
public class StandardEvaluation {

    String question1 = "How would you describe this team member’s overall contribution to the project?";
    String question2 = "How reliable and dependable was this person throughout the project?";
    String question3 = "Is there anything else you’d like to share about your experience working with this team member?";
    Boolean isGraded = true;
}
