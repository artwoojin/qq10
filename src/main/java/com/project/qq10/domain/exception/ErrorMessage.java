package com.project.qq10.domain.exception;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    WRONG_ID_PW("아이디와 비밀번호 확인이 필요합니다."),
    WRONG_PASSWORD("비밀번호가 일치하지 않습니다."),
    SAME_PASSWORD("새로운 비밀번호는 이전 비밀번호와 같을 수 없습니다."),

    USER_NOT_FOUND("사용자를 찾을 수 없습니다."),
    DUPLICATE_USERNAME("이미 사용중인 닉네임입니다."),
    DUPLICATE_EMAIL("이미 사용중인 이메일입니다."),
    TOKEN_ERROR("유효하지 않은 토큰입니다."),

    PRODUCT_NOT_FOUND("해당 상품을 찾을 수 없습니다."),
//    WISHLIST_PRODUCT_NOT_FOUND("해당 장바구니 상품을 찾을 수 없습니다."),
//    WISHLIST_NOT_FOUND("장바구니를 찾을 수 없습니다."),

    OUT_OF_STOCK("해당 상품은 품절되었습니다."),
    ORDER_NOT_FOUND("해당 주문 정보를 찾을 수 없습니다."),
    CANNOT_CANCEL_ORDER("배송중에는 주문 취소가 불가능합니다."),
    CANNOT_RETURN_ORDER_STATUS("배송이 완료되지 않은 상품은 반품이 불가능합니다."),
    CANNOT_RETURN_ORDER_DATE("배송완료 후 2일 이상 지난 상품은 반품이 불가능합니다."),
    ;

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
