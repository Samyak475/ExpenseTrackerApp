package com.codeartist.userservice.controllers;

import com.codeartist.userservice.dtos.UpdateRequestDto;
import com.codeartist.userservice.dtos.UpdateResponseDto;
import com.codeartist.userservice.entities.UserInfo;
import com.codeartist.userservice.services.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

public class UserController {
    @Autowired
    UserInfoService infoService;
    @GetMapping("/v1/users/{id}")
    public ResponseEntity<UserInfo> getUserFromId(@PathVariable(name = "id") Integer userId){
      UserInfo userInfo =  infoService.getUserById(userId);
      return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }


    @DeleteMapping("v1/users/{id}")
    public ResponseEntity deleteUserbyId(@PathVariable(name = "id") Integer userId){
         infoService.deleteUserById(userId);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PutMapping("v1/users/{id}")
    public  ResponseEntity<UpdateResponseDto> updateUserById( @PathVariable(name = "id") Integer userId, @RequestBody UpdateRequestDto  updateReqDto){
        updateReqDto.setUserId(userId);
      UpdateResponseDto updateResponseDto=  infoService.updateUserById(updateReqDto);
      return new ResponseEntity<>(updateResponseDto,HttpStatus.OK);
    }
}
