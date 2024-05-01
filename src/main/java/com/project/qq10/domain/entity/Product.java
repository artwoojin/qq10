package com.project.qq10.domain.entity;

import com.project.qq10.domain.exception.BusinessException;
import com.project.qq10.domain.exception.ErrorMessage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    public Long productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    public void decreaseQuantity(int quantity) {
        this.quantity -= quantity;

        if (this.quantity < 0) {
            throw new BusinessException(ErrorMessage.OUT_OF_STOCK);
        }
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }
}
