package us.cloud.teachme.studentservice.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import us.cloud.teachme.studentservice.domain.annotation.HttpStatusMapping;
import us.cloud.teachme.studentservice.domain.exception.DomainException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Object> handleDomainException(DomainException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        HttpStatusMapping mapping = ex.getClass().getAnnotation(HttpStatusMapping.class);
        if (mapping != null) {
            status = mapping.value();
        }

        return ResponseEntity.status(status).body(Map.of(
                "error", status.toString(),
                "message", ex.getMessage()
        ));
    }
}
