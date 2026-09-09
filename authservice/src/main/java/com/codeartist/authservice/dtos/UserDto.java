package com.codeartist.authservice.dtos;

import com.codeartist.authservice.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Type;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotBlank(message = "User name can not be blank")

    private String username;
    @NotBlank(message = "Email can not be blank")
    @Email(message = "Email need to have specific  format")
    private String emailId;
    @Size(message = "Password should contain min 3 char",min = 3)
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%&*?]).+$",
            message = "password should contain character , digit and special character"
    )
    private String password;
    private Integer userId;
    public  UserDto getUserDtoFromEntity(User user){

        this.emailId = user.getEmailId();
        this.password = user.getPassword();
        this.username = user.getUsername();
        this.userId = user.getUserId();
        return this;
    }
}
