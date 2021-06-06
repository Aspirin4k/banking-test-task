package com.banking.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class ErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(
            WebRequest request,
            ErrorAttributeOptions options
    ) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(request, options);

        Throwable error = super.getError(request);
        if (error instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> constraints
                    = ((ConstraintViolationException) error).getConstraintViolations();

            List<String> errors = new ArrayList<>(constraints.size());
            for (ConstraintViolation<?> constraintViolation : constraints) {
                errors.add(
                        constraintViolation.getPropertyPath() + " " +
                        constraintViolation.getMessage()
                );
            }

            errorAttributes.put("details", errors);
        }

        return errorAttributes;
    }
}
