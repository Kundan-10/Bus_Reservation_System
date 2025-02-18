package com.busbooking.BusReservationSystemPortal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Error> userException(UserException userEx, WebRequest web){
        Error error = new Error(LocalDateTime.now(),userEx.getMessage(), web.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<Error> loginException(LoginException userEx, WebRequest web){
        Error error = new Error(LocalDateTime.now(),userEx.getMessage(), web.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AdminException.class)
    public ResponseEntity<Error> adminException(AdminException userEx, WebRequest web) {
        Error error = new Error(LocalDateTime.now(), userEx.getMessage(), web.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusException.class)
    public ResponseEntity<Error> adminException(BusException userEx, WebRequest web) {
        Error error = new Error(LocalDateTime.now(), userEx.getMessage(), web.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> validatedException(MethodArgumentNotValidException validEx,WebRequest web){
        Error error = new Error(LocalDateTime.now(),validEx.getMessage(),validEx.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Error> noHandler(NoHandlerFoundException nohandler, WebRequest web){
        Error error = new Error(LocalDateTime.now(),nohandler.getMessage(), web.getDescription(false));
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> exception(Exception e,WebRequest web){
        Error error = new Error(LocalDateTime.now(),e.getMessage(), web.getDescription(false));
        return new ResponseEntity<>(error,HttpStatus.EXPECTATION_FAILED);
    }


}
