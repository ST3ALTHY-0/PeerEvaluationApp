package edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.sendResponseDataToClientDTO;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.Student;
import lombok.Data;

//This DTO is only used in the evaluationController getResponses method to pass
//evaluation response Data to the server.

//This is necessary to pass all the needed data to the client while
//avoiding looping connections that causes infinite recursion

@Data
public class EvaluationResponseDTO {

    private Integer responseId;
    private String responseText;
    private String questionText;
    private StudentDTO studentWhoReceivedFeedback;
    private StudentDTO studentWhoGaveFeedback;


}
