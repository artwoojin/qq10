package com.project.qq10.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private long userId;

    @Column(nullable = false,length = 100)
    private String username;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Order> orders = new ArrayList<>();

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    //private Long wishListId;

    public User(String username, String email, String password, String phoneNumber, String address, UserRoleEnum role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateAddress(String address) {
        this.address = address;
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /*
    * 비밀번호를 암호화
    * @param passwordEncoder 암호화할 인코더 클래스
    * @return 변경된 User Entity
    * */
    public User hashPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
        return this;
    }

    /*
    * 비밀번호 확인
    * @param basicPassword 암호화 이전 비밀번호
    * @param passwordEncoder 암호화에 사용된 클래스
    * */
    public void checkPassword(String basicPassword, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(this.password, basicPassword)) {
            throw new BadCredentialsException("Wrong password");
        }
    }
}
