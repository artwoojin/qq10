package com.project.qq10.domain.dto;

import com.project.qq10.domain.entity.Order;
import com.project.qq10.domain.entity.OrderStatus;
import com.project.qq10.domain.entity.OrderedProduct;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderResponseDto(
        long userId,
        List<OrderedProduct> orderedProductList,
        int quantity,
        int totalPrice,
        OrderStatus orderStatus
) {
    public static OrderResponseDto of(Order order) {
        return OrderResponseDto.builder()
                .userId(order.getUserId())
                .orderedProductList(order.getOrderedProductList())
                .totalPrice(order.getTotalPrice())
                .orderStatus(order.getStatus())
                .build();
    }
}
