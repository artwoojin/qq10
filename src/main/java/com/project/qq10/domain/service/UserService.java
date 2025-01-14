package com.project.qq10.domain.service;

//import com.project.qq10.domain.entity.Wishlist;
//import com.project.qq10.domain.repository.WishlistRepository;

import com.project.qq10.domain.dto.*;
import com.project.qq10.domain.entity.User;
import com.project.qq10.domain.entity.UserRoleEnum;
import com.project.qq10.domain.exception.BusinessException;
import com.project.qq10.domain.exception.ErrorMessage;
import com.project.qq10.domain.exception.NotFoundException;
import com.project.qq10.domain.repository.UserRepository;
import com.project.qq10.global.EncryptUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final EncryptUtil encryptUtil;


    /*
    * 회원가입
    * */
    public String signup(SignupRequestDto signupRequestDto) {
        Optional<User> findUserEmail = userRepository.findByEmail(signupRequestDto.getEmail());
        if (findUserEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        String userEmail = signupRequestDto.getEmail();
        String encodePassword = bCryptPasswordEncoder.encode(signupRequestDto.getPassword());
        String encodeName = encryptUtil.encrypt(signupRequestDto.getUsername());
        String encodeAddress = encryptUtil.encrypt(signupRequestDto.getAddress());
        String encodePhoneNumber = encryptUtil.encrypt(signupRequestDto.getPhoneNumber());

        User user = new User(encodeName,userEmail,encodePassword,encodePhoneNumber,encodeAddress,UserRoleEnum.USER);

        userRepository.save(user);
        return "signup_success";
    }

    /*
     * 로그인
     * */
    public LoginRequestDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND)
        );

        user.checkPassword(loginRequestDto.getPassword(), bCryptPasswordEncoder);

        TokenDto tokenDto = TokenDto.builder()
                .userId(user.getUserId())
                .build();
        TokenResponseDto tokenResponseDto = tokenManager.generateToken(tokenDto);

        return LoginRequestDto.builder()
                .email(user.getEmail())
                .accessToken(tokenResponse.get)
    }


    /*
     * 정보 수정(주소, 전화번호, 비밀번호)
     * */
    @Transactional
    public void updateAddress(String address, User user) {
        User findUser = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND)
        );

        String encodeAddress = encryptUtil.encrypt(address);
        findUser.updateAddress(encodeAddress);
    }

    @Transactional
    public void updatePhoneNumber(String phoneNumber, User user) {
        User findUser = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND)
        );

        findUser.updatePhoneNumber(phoneNumber);
    }

    @Transactional
    public void updatePassword(PasswordChangeRequestDto requestDto, User user) {
        User findUser = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND)
        );

        verificationPassword(requestDto, user);

        String encodedPassword = bCryptPasswordEncoder.encode(requestDto.password());
        findUser.updatePassword(encodedPassword);
    }


//    @Transactional
//    public String updateProfile(UpdateProfileRequestDto updateProfileRequestDto, User user) {
//
//        user = userRepository.findByEmail(user.getEmail()).orElseThrow(
//                () -> new NullPointerException("유저 정보를 찾지 못했습니다."));
//        //String encodeAddress = encryptUtil.encrypt(updateProfileRequestDto.getAddress());
//        String encodePhoneNumber = encryptUtil.encrypt(updateProfileRequestDto.getPhoneNumber());
//
//        user.updateAddress(encodeAddress, encodePhoneNumber);
//        return "updateAddress";
//    }


    /*
    * 기존 비밀번호 검증
    * */
    private void verificationPassword(PasswordChangeRequestDto requestDto, User user) {
        if (bCryptPasswordEncoder.matches(requestDto.password(), user.getPassword())) {
            throw new BusinessException(ErrorMessage.WRONG_PASSWORD);
        }

        if (bCryptPasswordEncoder.matches(requestDto.newPassword(), user.getPassword())) {
            throw new BusinessException(ErrorMessage.SAME_PASSWORD);
        }
    }

    /*
    * 정보 조회
    * */
    public MyPageResponseDto getMyPage(User user) {
        User findUser = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND)
        );

        String username = encryptUtil.decrypt(findUser.getUsername());
        String email = encryptUtil.decrypt(findUser.getEmail());
        String address = encryptUtil.decrypt(findUser.getAddress());

        return MyPageResponseDto.builder()
                .username(findUser.getUsername())
                .email(email)
                .address(address)
                .phoneNumber(findUser.getPhoneNumber())
                .build();
    }





}
