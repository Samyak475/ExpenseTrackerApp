package com.codeartist.userservice.controllers;

import com.codeartist.userservice.entities.UserInfo;
import com.codeartist.userservice.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

public class UserController {
    @Autowired
    UserInfoService infoService;
    @GetMapping("/v1/users/{id}")
    public ResponseEntity<UserInfo> getUserFromId(@PathVariable(name = "id") String id){
      UserInfo userInfo =  infoService.getUserById(id);
      return new ResponseEntity<>(userInfo, HttpStatus.FOUND);
    }


    @DeleteMapping("v1/users/{id}")
    public ResponseEntity deleteUserbyId(@PathVariable(name = "id") String id){
         infoService.deleteUserById(id);
        return new ResponseEntity<>( HttpStatus.FOUND);
    }
}
