package com.project.qq10.domain.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@DiscriminatorValue("GENERAL")
@NoArgsConstructor
//@SuperBuilder
public class GeneralProduct extends Product {
}
