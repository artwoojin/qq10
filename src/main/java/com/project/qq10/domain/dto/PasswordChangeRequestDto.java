package com.project.qq10.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record PasswordChangeRequestDto (

        @NotBlank(message = "비밀번호를 입력해주세요")
        String password,

        @NotBlank(message = "비밀번호를 입력해주세요")
        String newPassword
) { }
