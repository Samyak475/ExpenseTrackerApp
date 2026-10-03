package com.codeartist.userservice.deserializer;

import com.codeartist.userservice.dtos.ConsumerUserDto;

import org.apache.kafka.common.serialization.Deserializer;

import tools.jackson.databind.ObjectMapper;

public class ConsumerDeserializer implements Deserializer<ConsumerUserDto> {

    private  final ObjectMapper objectMapper= new ObjectMapper();
    @Override
    public ConsumerUserDto deserialize(String s, byte[] bytes) {
        return objectMapper.readValue(bytes, ConsumerUserDto.class);
    }
}
