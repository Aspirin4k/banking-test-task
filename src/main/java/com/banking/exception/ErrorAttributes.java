package com.banking.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class ErrorAttributes extends DefaultErrorAttributes {
    private final String ERROR_DETAILS_ATTRIBUTE = "details";

    @Override
    public Map<String, Object> getErrorAttributes(
            WebRequest request,
            ErrorAttributeOptions options
    ) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(request, options);

        Throwable error = super.getError(request);
        if (error instanceof ConstraintViolationException) {
            errorAttributes.put(
                    this.ERROR_DETAILS_ATTRIBUTE,
                    this.handleConstraintViolation((ConstraintViolationException) error)
            );
        }

        if (error instanceof MethodArgumentNotValidException) {
            errorAttributes.put(
                    this.ERROR_DETAILS_ATTRIBUTE,
                    this.handleMethodArgumentNotValid((MethodArgumentNotValidException) error)
            );
        }

        if (error instanceof DataIntegrityViolationException) {
            errorAttributes.put(
                    this.ERROR_DETAILS_ATTRIBUTE,
                    this.handleSQLIntegrityViolation((DataIntegrityViolationException) error)
            );
        }

        return errorAttributes;
    }

    private List<String> handleConstraintViolation(ConstraintViolationException error)
    {
        Set<ConstraintViolation<?>> constraints = error.getConstraintViolations();

        List<String> errors = new ArrayList<>(constraints.size());
        for (ConstraintViolation<?> constraintViolation : constraints) {
            errors.add(
                    constraintViolation.getPropertyPath() + " " +
                            constraintViolation.getMessage()
            );
        }

        return errors;
    }

    private List<String> handleMethodArgumentNotValid(MethodArgumentNotValidException error)
    {
        List<ObjectError> bindings = error.getBindingResult().getAllErrors();

        List<String> errors = new ArrayList<>(bindings.size());
        for (ObjectError bindingError : bindings) {
            if (bindingError instanceof FieldError) {
                errors.add(
                        ((FieldError) bindingError).getField() + " "
                                + bindingError.getDefaultMessage()
                );
            } else {
                errors.add(bindingError.toString());
            }
        }

        return errors;
    }

    private List<String> handleSQLIntegrityViolation(DataIntegrityViolationException error)
    {
        List<String> errors = new ArrayList<>();
        errors.add(
                null == error.getRootCause()
                    ? error.getLocalizedMessage()
                    : error.getRootCause().getLocalizedMessage()
        );
        return errors;
    }
}
