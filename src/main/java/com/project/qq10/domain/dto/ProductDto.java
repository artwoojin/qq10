package com.project.qq10.domain.dto;

import com.project.qq10.domain.entity.GeneralProduct;
import com.project.qq10.domain.entity.Product;
import com.project.qq10.domain.entity.ReservedProduct;
import lombok.Builder;

public class ProductDto {
    private Long id;
    private String name;
    private int price;
    private String type;

    @Builder
    public ProductDto(Long id, String name, int price, String type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public static ProductDto of(Product product) {
        String type = product instanceof GeneralProduct ? "GENERAL" : product instanceof ReservedProduct ? "RESERVED" : "UNKNOWN";
        return ProductDto.builder()
                .id(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .type(type)
                .build();
    }
}
