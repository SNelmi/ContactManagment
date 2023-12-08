package com.contact.contactmanagement.controller;
import com.contact.contactmanagement.exceptions.CompanyException;
import com.contact.contactmanagement.exceptions.ContactException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice(basePackageClasses = ContactController.class)
public class RestExceptionHandlerController {

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        if (e instanceof ContactException contactException) {
            return new ResponseEntity<>(new ErrorResponse(contactException.getStatus(), contactException.errorMessage()), contactException.getStatus());
        } else if (e instanceof CompanyException companyException) {
            return new ResponseEntity<>(new ErrorResponse(companyException.getStatus(), companyException.errorMessage()), companyException.getStatus());
        } else if (e instanceof DataIntegrityViolationException) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.CONFLICT,"Another entity with the same identity exists"), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,"Unexpected Exception: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Intercept validation error
     *
     * @param ex
     *          MethodArgumentNotValidException
     * @return list of field causing the error
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @Getter
    @Setter
    static class ErrorResponse {

        private int status;
        private String code;
        private String message;

        public ErrorResponse(HttpStatus httpStatus, String code, String message) {
            this.status = httpStatus.value();
            this.code = code;
            this.message = message;
        }
        public ErrorResponse(HttpStatus httpStatus, String message) {
            this(httpStatus, httpStatus.name(), message);
        }
    }
}
