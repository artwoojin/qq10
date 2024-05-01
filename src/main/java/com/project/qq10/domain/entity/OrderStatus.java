package com.project.qq10.domain.entity;

public enum OrderStatus {
    CREATED("주문 완료"),
    SHIPPING("배송 진행중"),
    COMPLETED("배송 완료"),
    RETURNING("반품 진행중"),
    RETURNED("반품 완료"),
    CANCELED("취소 완료");

    OrderStatus(String status) {
    }
}
