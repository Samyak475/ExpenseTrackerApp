package com.codeartist.authservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String,String>>handleMalFormedJson(HttpMessageNotReadableException e){
//        ResponseEntity response = new ResponseEntity();
        HashMap<String,String> errorMessage = new HashMap<>();
        errorMessage.put("error","Bad Request");
        errorMessage.put("message","Provide Json in Request contain incorrect fields" +e.getLocalizedMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, String>>handleUserError(UsernameNotFoundException e){
        HashMap<String,String>error = new HashMap<>();
        error.put("error","Not Found");
        error.put("message","Requested User Details not found"+e.getLocalizedMessage());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, String>>handleJsonNullException(NullPointerException e){
        HashMap<String,String>error = new HashMap<>();
        error.put("error","Bad request");
        error.put("message","Null value found"+e.getLocalizedMessage());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>>handleAuthException(RuntimeException e){
        HashMap<String,String>error = new HashMap<>();
        error.put("error","Authentication failure");
        error.put("message","Authentication failure due to following reason  :- "+e.getMessage());
        return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Map<String, String>>handleDuplicateUser(UserAlreadyExistException e){
        HashMap<String,String>error = new HashMap<>();
        error.put("error","Conflict");
        error.put("message","User already exist in db"+e.getMessage());
        return new ResponseEntity<>(error,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>>MethodNotValidException(MethodArgumentNotValidException e){
        HashMap<String,String>error = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(er ->{
            error.put(er.getField(),er.getDefaultMessage());
        });

        return new ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE);
    }

}
