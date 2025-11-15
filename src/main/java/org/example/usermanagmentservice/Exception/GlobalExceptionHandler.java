package org.example.usermanagmentservice.Exception;

import org.example.usermanagmentservice.entity.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleNotFoundUser(UserNotFoundException ex) {
        var httpStatus = HttpStatus.NOT_FOUND;
        var errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                httpStatus.value(),
                "Resource Not Found",
                ex.getMessage()
        );
        return new ResponseEntity<>(errorDetails, httpStatus);
    }
    @ExceptionHandler(AlreadyUserException.class)
    public ResponseEntity<ErrorDetails>handleAlreadyUser(AlreadyUserException ex){
        var httpStatus=HttpStatus.ALREADY_REPORTED;
        var erordetails=new ErrorDetails(LocalDateTime.now(),httpStatus.value(),"Already User",ex.getMessage());
        return new ResponseEntity<>(erordetails,httpStatus);
    }

}


