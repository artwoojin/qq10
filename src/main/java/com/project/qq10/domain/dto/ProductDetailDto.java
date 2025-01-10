package com.project.qq10.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.qq10.domain.entity.GeneralProduct;
import com.project.qq10.domain.entity.Product;
import com.project.qq10.domain.entity.ReservedProduct;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDetailDto {
    private Long id;
    private String name;
    private String content;
    private int price;
    private int quantity;
    private String type; // "GENERAL" 또는 "RESERVED"
    private LocalDateTime reservationStart; // 예약 시작 시간
    private LocalDateTime reservationEnd; // 예약 종료 시간

    @Builder
    public ProductDetailDto(Long id, String name, String content, int price, int quantity, String type,
                         LocalDateTime reservationStart, LocalDateTime reservationEnd) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
    }

    public static ProductDetailDto of(Product product) {
        String type = product instanceof GeneralProduct ? "GENERAL" : product instanceof ReservedProduct ? "RESERVED" : "UNKNOWN";
        LocalDateTime reservationStart = null;
        LocalDateTime reservationEnd = null;

        if (product instanceof ReservedProduct) {
            ReservedProduct reservedproduct = (ReservedProduct) product;
            reservationStart = reservedproduct.getReservationStart();
            reservationEnd = reservedproduct.getReservationEnd();
        }

        return ProductDetailDto.builder()
                .id(product.getProductId())
                .name(product.getName())
                .content(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .type(type)
                .reservationStart(reservationStart)
                .reservationEnd(reservationEnd)
                .build();
    }
}
