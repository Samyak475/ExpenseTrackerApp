package com.codeartist.authservice.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;


public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message){
        super(message);
    }
}
