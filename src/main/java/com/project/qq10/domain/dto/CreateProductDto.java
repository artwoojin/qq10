package com.project.qq10.domain.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateProductDto {
    private String name;
    private String description;
    private int price;
    private int quantity;
    private String type; // "GENERAL" 또는 "RESERVED"
    private LocalDateTime reservationStart;
    private LocalDateTime reservationEnd;
}
