package com.banking.controller;

import com.banking.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.ConstraintViolationException;
import java.io.IOException;

@ControllerAdvice
public class ValidationHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public void handleConstraintViolationException(
            ConstraintViolationException exception,
            ServletWebRequest webRequest
    ) throws IOException {
        webRequest.getResponse().sendError(HttpStatus.BAD_REQUEST.value(), exception.getLocalizedMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFoundException(EntityNotFoundException exception,
                                              ServletWebRequest webRequest) throws IOException {
        webRequest.getResponse().sendError(HttpStatus.NOT_FOUND.value(), exception.getLocalizedMessage());
    }
}
