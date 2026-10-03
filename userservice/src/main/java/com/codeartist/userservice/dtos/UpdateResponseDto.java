package com.codeartist.userservice.dtos;

import com.codeartist.userservice.entities.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateResponseDto {
    private   Integer userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private int age;
    private String curType;
    private Integer phoneNo;

    public UpdateResponseDto getUpdateRespDto(UserInfo userInfo){
        return  UpdateResponseDto.builder()
                .age(userInfo.getAge())
                .userId(userInfo.getUserId())
                .curType(userInfo.getCurType())
                .phoneNo(userInfo.getPhoneNo())
                .emailId(userInfo.getEmailId())
                .firstName(userInfo.getFirstName())
                .lastName(userInfo.getLastName())
                .build();
    }

}
