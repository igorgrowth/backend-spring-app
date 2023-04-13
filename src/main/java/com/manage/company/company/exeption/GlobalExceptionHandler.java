package com.manage.company.company.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request){
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<?> handleEntityAlreadyExist(EntityAlreadyExistException exception, WebRequest request){
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionDetails, HttpStatus.CONFLICT);
    }

}
