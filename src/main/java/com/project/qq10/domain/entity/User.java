package com.project.qq10.domain.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "userId")
    private long userId;

    private String userName;

    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    private long phoneNumber;

    private String address;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();
}
