package com.codeartist.authservice.producer;

import com.codeartist.authservice.dtos.UserCreatedEvntDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserKafkaListener {
    @Autowired
    public KafkaTemplate<String,UserCreatedEvntDto> kafkaTemplate;
    private final static String topic = "user-creation-topic";
    public void sendToProducer(UserCreatedEvntDto userCreatedEvntDto){
        kafkaTemplate.send(topic,userCreatedEvntDto.getEmail(),userCreatedEvntDto);
    }

}
