package com.project.qq10.domain.exception;

import lombok.Getter;

public enum ExceptionCode {

    USER_EXISTS(1, "이미 등록된 이메일입니다."),
    USER_NOT_FOUND(2, "존재하지 않는 회원입니다.");
    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
