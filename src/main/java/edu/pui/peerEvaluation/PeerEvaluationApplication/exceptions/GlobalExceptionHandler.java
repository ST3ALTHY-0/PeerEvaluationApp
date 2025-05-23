package edu.pui.peerEvaluation.PeerEvaluationApplication.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorDetails handleException(EntityNotFoundException ex) {
        return new ErrorDetails(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
    
}
