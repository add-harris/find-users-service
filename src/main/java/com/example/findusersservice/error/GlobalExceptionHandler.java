package com.example.findusersservice.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.findusersservice.config.Constants.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleWebClientError(Exception exception) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(500);

        if (exception.getMessage().contains(INTERNAL_SERVER_ERROR) || exception.getMessage().contains(NOT_FOUND) ) {
            errorResponse.setMessage(PROBLEM_UPSTREAM);
        } else if (exception.getMessage().contains(JSON_DECODING_ERROR)) {
            errorResponse.setMessage(UNEXPECTED_RESPONSE);
        } else {
            errorResponse.setMessage(UNEXPECTED_ERROR);
        }

        return ResponseEntity.internalServerError().body(errorResponse);
    }

}
