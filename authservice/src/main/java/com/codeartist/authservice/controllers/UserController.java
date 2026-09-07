package com.codeartist.authservice.controllers;

import com.codeartist.authservice.dtos.SignUpResponseDto;
import com.codeartist.authservice.dtos.UserDto;
import com.codeartist.authservice.services.SignUpServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserController {
    @Autowired
    SignUpServiceHandler signUpServiceHandler ;
    @GetMapping("/v1/signUp")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody UserDto userDto) {
       SignUpResponseDto token = signUpServiceHandler.signUpRequest(userDto);
        return new ResponseEntity<>(token,HttpStatus.OK);
    }

    @GetMapping("/v1/login")
    public  ResponseEntity<String>login(@RequestBody UserDto userDto){
        signUpServiceHandler.loginRequest(userDto);
        return new ResponseEntity<>("String" ,HttpStatus.OK);
    }
}
