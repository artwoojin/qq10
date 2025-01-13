package com.project.qq10.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    private String email;
    private String password;
    private String accessToken;
    private String refreshToken;
}
