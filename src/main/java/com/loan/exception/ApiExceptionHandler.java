package com.loan.exception;

import com.loan.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({ArithmeticException.class})
    ResponseEntity<ExceptionResponse> handleArithmeticException(ArithmeticException ex) {
        var response = new ExceptionResponse();
        response.setSummary("Got arithmetic exception");
        response.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    ResponseEntity<ExceptionResponse> handleRequestBodyNotReadable(HttpMessageNotReadableException ex) {
        var response = new ExceptionResponse();
        response.setSummary("Cannot read HTTP request body. Check whether you submit valid JSON");
        response.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({Exception.class})
    ResponseEntity<ExceptionResponse> handleAnyException(Exception ex) {
        var response = new ExceptionResponse();
        response.setSummary("Got exception");
        response.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    @ExceptionHandler({LoanBusinessException.class})
    ResponseEntity<ExceptionResponse> handleLoanBusinessException(LoanBusinessException ex) {
        var response = new ExceptionResponse();
        response.setSummary("Got loan business exception");
        response.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({LoanOwnerException.class})
    ResponseEntity<ExceptionResponse> handleLoanOwnerException(LoanOwnerException ex) {
        var response = new ExceptionResponse();
        response.setSummary("This loan is forbidden");
        response.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var message = ex.getFieldErrors().stream().map(f -> f.getField() + " (value: " + f.getRejectedValue() +
                ") " + f.getDefaultMessage()).collect(Collectors.joining(", "));
        var response = new ExceptionResponse();
        response.setSummary("Invalid input arguments");
        response.setMessage(message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
