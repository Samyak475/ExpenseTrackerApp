package com.codeartist.userservice.dtos;

import com.codeartist.userservice.entities.UserInfo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateRequestDto {

    private   Integer userId;
    private String firstName;
    private String lastName;
    @Email
    private String emailId;
    private int age;
    private String curType;
    private Integer phoneNo;

}
