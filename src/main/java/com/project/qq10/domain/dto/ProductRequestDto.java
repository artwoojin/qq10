package com.project.qq10.domain.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ProductRequestDto(
        String name,
        String description,
        int price,
        int quantity,
        String type,
        LocalDateTime reservationStart,
        LocalDateTime reservationEnd
) { }
