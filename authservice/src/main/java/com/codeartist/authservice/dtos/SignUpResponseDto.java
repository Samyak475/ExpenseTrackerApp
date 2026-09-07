package com.codeartist.authservice.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SignUpResponseDto {
    private String accessToken;
    private String refreshToken;
}
