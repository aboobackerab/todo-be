package com.Todo.exceptions.globalhandler;


import com.Todo.exceptions.EmailAlreadyExistException;
import com.Todo.exceptions.UserAdditionException;
import com.Todo.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<?> handleEmailAlreadyExistException(EmailAlreadyExistException ex) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAdditionException.class)
    public ResponseEntity<?> handleRUserAdditionException(UserAdditionException ex) {
        Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
