package com.codeartist.authservice.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class LoginRequestDto {
@NonNull
    private String username;
@NonNull
    private String password;
}
