package edu.pui.peerEvaluation.PeerEvaluationApplication.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private int statusCode;
    private String message;
    private String details;

    public ErrorDetails(int statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
    }
}
