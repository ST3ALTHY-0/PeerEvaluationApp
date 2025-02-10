package edu.pui.peerEvaluation.Peerevualuationapplication.orm.student;

import edu.pui.peerEvaluation.Peerevualuationapplication.exception.ErrorDetails;
import edu.pui.peerEvaluation.Peerevualuationapplication.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    






    

    @ExceptionHandler(value = EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDetails handleCustomerAlreadyExistsException(EntityNotFoundException ex) {
        return new ErrorDetails(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

}
