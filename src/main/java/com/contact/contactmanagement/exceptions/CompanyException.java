package com.contact.contactmanagement.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CompanyException extends RuntimeException{

    private final HttpStatus status;


    public CompanyException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public String errorMessage(){
        return status.value() + ":".concat(getMessage());
    }

}
