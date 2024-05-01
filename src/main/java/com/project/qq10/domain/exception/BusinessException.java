package com.project.qq10.domain.exception;

import lombok.Getter;

public class BusinessException extends RuntimeException {

    @Getter
    private ExceptionCode exceptionCode;

    public BusinessException(ErrorMessage exceptionCode) {
        super(exceptionCode.getMessage());
    }
}
