package com.codeartist.authservice.dtos;

import com.codeartist.authservice.entities.Tokens;
import com.codeartist.authservice.entities.User;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String userName;
    private String userEmailId;
    private String userEmailPassword;
    private Integer userId;
    public  UserDto getUserDtoFromEntity(User user){

        this.userEmailId = user.getEmailId();
        this.userEmailPassword = user.getPassword();
        this.userName = user.getUsername();
        this.userId = user.getUserId();
        return this;
    }
}
