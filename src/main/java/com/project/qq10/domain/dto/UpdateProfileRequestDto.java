package com.project.qq10.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateProfileRequestDto {
    @NotBlank(message = "주소를 입력해주세요.")
    private String address;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phoneNumber;
}
