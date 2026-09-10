package com.codeartist.userservice.consumer;

import com.codeartist.userservice.dtos.ConsumerUserDto;
import com.codeartist.userservice.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserEventListener {
    @Autowired
    UserInfoService userInfoService;

    @KafkaListener(topics = "user-creation-topic",groupId = "user-service-group")
    public void handleUserCreationn(ConsumerUserDto consumerUserDto){
        userInfoService.saveUserDetailsFromAuth(consumerUserDto);
    }
}
