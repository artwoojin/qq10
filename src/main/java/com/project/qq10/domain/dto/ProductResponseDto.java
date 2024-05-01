package com.project.qq10.domain.dto;

import com.project.qq10.domain.entity.Product;
import lombok.Builder;

@Builder
public record ProductResponseDto(
        long productId,
        String name,
        String description,
        int price,
        int quantity
) {
    public static ProductResponseDto toListResponseDto(Product product) {
        return ProductResponseDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }

    public static ProductResponseDto of(Product product) {
        return ProductResponseDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }
}
