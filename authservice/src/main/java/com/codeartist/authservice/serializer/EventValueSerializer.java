package com.codeartist.authservice.serializer;

import com.codeartist.authservice.dtos.UserCreatedEvntDto;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import tools.jackson.databind.ObjectMapper;


public class EventValueSerializer implements Serializer<UserCreatedEvntDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(String s, UserCreatedEvntDto userCreatedEvntDto) {
        try{
        return objectMapper.writeValueAsBytes(userCreatedEvntDto);
        }
        catch (Exception e){
            throw  new SerializationException("unable to serialise the event in kafka");
        }

    }
}
