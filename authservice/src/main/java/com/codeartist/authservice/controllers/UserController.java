package com.codeartist.authservice.controllers;

import com.codeartist.authservice.dtos.LoginRequestDto;
import com.codeartist.authservice.dtos.LoginResponseDto;
import com.codeartist.authservice.dtos.SignUpResponseDto;
import com.codeartist.authservice.dtos.UserDto;
import com.codeartist.authservice.services.AuthService;
import com.codeartist.authservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserController {
    @Autowired
    AuthService authService;
    @PostMapping("/v1/signUp")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody UserDto userDto) {
       SignUpResponseDto token = authService.signUpRequest(userDto);
        return new ResponseEntity<>(token,HttpStatus.OK);
    }

    @PostMapping("/v1/login")
    public  ResponseEntity<LoginResponseDto>login(@RequestBody LoginRequestDto loginRequestDto){
        LoginResponseDto loginResponseDto = authService.loginRequest(loginRequestDto);
        return new ResponseEntity<>(loginResponseDto ,HttpStatus.OK);
    }
}
