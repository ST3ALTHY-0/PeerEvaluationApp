package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.pui.peerEvaluation.PeerEvaluationApplication.exception.EntityNotFoundException;
import edu.pui.peerEvaluation.PeerEvaluationApplication.exception.ErrorDetails;

@RestController
public class StudentController {

    






    

    @ExceptionHandler(value = EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDetails handleCustomerAlreadyExistsException(EntityNotFoundException ex) {
        return new ErrorDetails(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

}
