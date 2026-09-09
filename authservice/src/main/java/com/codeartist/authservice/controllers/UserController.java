package com.codeartist.authservice.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeartist.authservice.dtos.LoginRequestDto;
import com.codeartist.authservice.dtos.LoginResponseDto;
import com.codeartist.authservice.dtos.SignUpResponseDto;
import com.codeartist.authservice.dtos.UserDto;
import com.codeartist.authservice.services.AuthService;

@RestController
@RequestMapping
public class UserController {
    @Autowired
    AuthService authService;
    @PostMapping("/v1/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@Valid @RequestBody UserDto userDto) {
//        try{
            SignUpResponseDto token = authService.signUpRequest(userDto);
            return new ResponseEntity<>(token,HttpStatus.OK);
//        }
//      catch (Exception e){
//          SignUpResponseDto loginResponseDto = SignUpResponseDto.builder().accessToken("Got problem while authentication"+e.getMessage()).build();
//          return new ResponseEntity<>(loginResponseDto,HttpStatus.UNAUTHORIZED);
//      }

    }

    @PostMapping("/v1/login")
    public  ResponseEntity<LoginResponseDto>login(@Valid @RequestBody LoginRequestDto loginRequestDto){
//        try{
        LoginResponseDto loginResponseDto = authService.loginRequest(loginRequestDto);
        return new ResponseEntity<>(loginResponseDto ,HttpStatus.OK);
//         }
//      catch (Exception e){
//            LoginResponseDto loginResponseDto = LoginResponseDto.builder().username("Got problem while authentication"+e.getMessage()).build();
//        return new ResponseEntity<>(loginResponseDto,HttpStatus.UNAUTHORIZED);
//        }
    }
}
