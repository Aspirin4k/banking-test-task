package com.banking.controller.advice;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@ControllerAdvice
public class ThrowableHandler {
    @ExceptionHandler(Throwable.class)
    public @ResponseBody ExceptionResponse handle(HttpServletResponse response, Throwable throwable) {
        HttpStatus status = Optional
                .ofNullable(AnnotationUtils.getAnnotation(throwable.getClass(), ResponseStatus.class))
                .map(ResponseStatus::value)
                .orElse(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setStatus(status.value());
        return new ExceptionResponse(throwable.getMessage(), status.value());
    }
}

