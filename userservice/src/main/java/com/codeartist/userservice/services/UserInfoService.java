package com.codeartist.userservice.services;

import com.codeartist.userservice.dtos.ConsumerUserDto;
import com.codeartist.userservice.entities.UserInfo;
import com.codeartist.userservice.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService {
    @Autowired
    UserInfoRepository userRepo;
        public UserInfo getUserById(String userId){
            Optional<UserInfo> existingUser = userRepo.getUserInfoByUserId(userId);
            if(existingUser.isEmpty()) {
                throw new RuntimeException("user not present with given userId");
            }
            return existingUser.get();
        }

    public void deleteUserById(String userId){
        userRepo.deleteById(userId);
    }
    public void saveUserDetailsFromAuth(ConsumerUserDto consumerUserDto){

            userRepo.save(getUserInfoFromConsumerDto(consumerUserDto));
    }
    public  UserInfo getUserInfoFromConsumerDto(ConsumerUserDto consumerUserDto){
            String []name = consumerUserDto.getUsername().split(" ");

            return new UserInfo().builder()
                    .userId(consumerUserDto.getUserId())
                    .firstName(name[0])
                    .lastName(name[1])
                    .emailId(consumerUserDto.getEmail())
                    .build();


    }
}
