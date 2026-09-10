package com.codeartist.userservice.deserializer;

import com.codeartist.userservice.dtos.ConsumerUserDto;
import com.codeartist.userservice.services.UserInfoService;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.beans.factory.annotation.Autowired;
import tools.jackson.databind.ObjectMapper;

public class ConsumerDeserializer implements Deserializer<ConsumerUserDto> {

    private  final ObjectMapper objectMapper= new ObjectMapper();
    @Override
    public ConsumerUserDto deserialize(String s, byte[] bytes) {
       ConsumerUserDto consumerUserDto= objectMapper.readValue(bytes, ConsumerUserDto.class);

        return consumerUserDto;
    }
}
