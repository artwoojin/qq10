package com.project.qq10.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponseDto {
    @Getter
    public String access_token;    //액세스 토큰
    public String refresh_token;   //리프레쉬 토큰
    public long expires_in;        //토큰 만료시간

}
