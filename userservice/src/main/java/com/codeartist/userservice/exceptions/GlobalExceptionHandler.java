package com.codeartist.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,String>> handleDBExceptions(IllegalArgumentException  e){
        Map<String ,String>  errorMes = new HashMap<>();
        errorMes.put("Error ","Given Details about user are incorrect ");
        errorMes.put("error stack   ",e.getMessage());
        return new ResponseEntity<>(errorMes, HttpStatus.NOT_FOUND);
    }
}
