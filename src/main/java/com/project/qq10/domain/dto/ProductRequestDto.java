package com.project.qq10.domain.dto;

import lombok.Builder;

@Builder
public record ProductRequestDto(
        String name,
        String description,
        int price,
        int quantity
) { }
