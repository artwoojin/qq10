package com.project.qq10.domain.service;

import com.project.qq10.global.EncryptUtil;
import com.project.qq10.domain.dto.UpdateProfileRequestDto;
import com.project.qq10.domain.entity.UserRoleEnum;
import com.project.qq10.domain.dto.SignupRequestDto;
import com.project.qq10.domain.entity.User;
import com.project.qq10.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EncryptUtil encryptUtil;


    public String signup(SignupRequestDto signupRequestDto) {
        Optional<User> findUserEmail = userRepository.findByEmail(signupRequestDto.getEmail());
        if (findUserEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        String userEmail = signupRequestDto.getEmail();
        String encodePassword = passwordEncoder.encode(signupRequestDto.getPassword());
        String encodeName = encryptUtil.encrypt(signupRequestDto.getUsername());
        String encodeAddress = encryptUtil.encrypt(signupRequestDto.getAddress());
        String encodePhoneNumber = encryptUtil.encrypt(signupRequestDto.getPhoneNumber());

        //Wishlist wishlist = wishlistRepository.save(new Wishlist());

        User user = new User(encodeName,userEmail,encodePassword,encodePhoneNumber,encodeAddress,UserRoleEnum.USER);

        userRepository.save(user);
        return "signup_success";
    }

    @Transactional
    public String updateProfile(UpdateProfileRequestDto updateProfileRequestDto, User user) {
        user = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new NullPointerException("유저 정보를 찾지 못하였습니다."));
        String encodeAddress = encryptUtil.encrypt(updateProfileRequestDto.getAddress());
        String encodePhoneNumber = encryptUtil.encrypt(updateProfileRequestDto.getPhoneNumber());

        user.updateProfile(encodeAddress, encodePhoneNumber);
        return "updateProfile";
    }


}
