package com.contact.contactmanagement.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ContactException extends RuntimeException{

    private final HttpStatus status;

    public ContactException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public String errorMessage(){
        return status.value() + ":".concat(getMessage());
    }

}
