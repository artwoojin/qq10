package com.project.qq10.domain.dto;

import lombok.Builder;

@Builder
public record MyPageResponseDto(

        String username,
        String name,
        String email,
        String address,
        String phoneNumber
) { }
