package learn.htdbank.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionalHandler {
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> HandleException(DataAccessException exception) {
        return new ResponseEntity<>(new ErrorResponse("Something went wrong in the data layer."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> HandleException(EmptyResultDataAccessException exception) {
        return new ResponseEntity<>(new ErrorResponse("Data does not exists in the database."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> HandleException(Exception exception) {
        return new ResponseEntity<>(new ErrorResponse("Something went wrong. Please contact the IT person."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
