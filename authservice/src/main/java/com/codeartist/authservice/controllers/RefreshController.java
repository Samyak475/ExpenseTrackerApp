package com.codeartist.authservice.controllers;

import com.codeartist.authservice.services.AuthService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RefreshController {
    @Autowired
    AuthService authService;
    @PostMapping("/v1/refreshToken")
    public ResponseEntity<Map<String,String>> getAccessFromRefreshToken(@RequestBody Map<String,String> refreshToken){
        Map<String,String>response = new HashMap<>();
       String accessToken= authService.generateAccessToken( refreshToken.get("refreshToken"));
       response.put("accessToken",accessToken);
       return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
