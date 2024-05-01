package com.project.qq10.domain.dto;

import lombok.Builder;

@Builder
public record OrderRequestDto (
        long productId,
        int quantity
) { }
