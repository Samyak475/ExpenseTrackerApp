package com.codeartist.userservice.services;

import com.codeartist.userservice.dtos.ConsumerUserDto;
import com.codeartist.userservice.dtos.UpdateRequestDto;
import com.codeartist.userservice.dtos.UpdateResponseDto;
import com.codeartist.userservice.entities.UserInfo;
import com.codeartist.userservice.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserInfoService {
   private final UserInfoRepository userRepo;
   @Autowired
    public  UserInfoService(UserInfoRepository userRepo){
        this.userRepo = userRepo;
    }


        public UserInfo getUserById(Integer userId){
            Optional<UserInfo> existingUser = userRepo.getUserInfoByUserId(userId);
            if(existingUser.isEmpty()) {
                throw new IllegalArgumentException("user not present with given userId");
            }
            return existingUser.get();
        }
    @Transactional
    public void deleteUserById(Integer userId){
       try {
           userRepo.deleteById(userId);
       }catch (Exception e){
           throw new IllegalArgumentException("Unable to delete the user with given user Id "+e);
       }
    }

    @Transactional
    public void saveUserDetailsFromAuth(ConsumerUserDto consumerUserDto){

            userRepo.save(getUserInfoFromConsumerDto(consumerUserDto));
    }

    @Transactional
    public UpdateResponseDto updateUserById(UpdateRequestDto updateRequestDto){
       UserInfo curUserInfo =getUserInfoFromReqDto(updateRequestDto);
     Optional <UserInfo> existingUser = userRepo.findById(curUserInfo.getUserId());
     if(existingUser.isEmpty()){
         throw  new IllegalArgumentException("User with given id does not exit");
     }
        try{
       curUserInfo= userRepo.save(curUserInfo);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to save user details",e);
        }
        return new UpdateResponseDto().getUpdateRespDto(curUserInfo);

    }

    public UserInfo getUserInfoFromReqDto(UpdateRequestDto updateRequestDto){
        return  UserInfo.builder()
                .age(updateRequestDto.getAge())
                .userId(updateRequestDto.getUserId())
                .curType(updateRequestDto.getCurType())
                .phoneNo(updateRequestDto.getPhoneNo())
                .emailId(updateRequestDto.getEmailId())
                .firstName(updateRequestDto.getFirstName())
                .lastName(updateRequestDto.getLastName())
                .build();
    }

    public  UserInfo getUserInfoFromConsumerDto(ConsumerUserDto consumerUserDto){
            String []name = consumerUserDto.getUsername().split(" ");
            String lastName = name.length>1?name[name.length-1]:"";
            return UserInfo.builder()
                    .userId(consumerUserDto.getUserId())
                    .firstName(name[0])
                    .lastName(lastName)
                    .emailId(consumerUserDto.getEmail())
                    .build();
    }
}
