package edu.pui.peerEvaluation.PeerEvaluationApplication.exceptions;

public class InstructorAlreadyExistsException extends RuntimeException {
    public InstructorAlreadyExistsException(String message) {
        super(message);
    }
}