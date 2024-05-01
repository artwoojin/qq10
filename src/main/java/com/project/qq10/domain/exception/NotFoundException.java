package com.project.qq10.domain.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(ErrorMessage message) {
        super(message.getMessage());
    }
}
