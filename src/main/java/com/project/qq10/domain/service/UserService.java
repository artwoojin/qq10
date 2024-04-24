package com.project.qq10.domain.service;

import com.project.qq10.domain.entity.User;
import com.project.qq10.domain.exception.BusinessException;
import com.project.qq10.domain.exception.ExceptionCode;
import com.project.qq10.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // (1) user 등록(회원 가입)
    public User createUser(User user) {

        // 이미 등록된 이메일인지 검증
        verifyExistsEmail(user.getEmail());

        // 회원가입시 기본 role = user 로 설정
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        user.setRoles(roles);

        // 회원정보 저장
        return userRepository.save(user);
    }

    // 이미 등록된 이메일인지 검증
    private void verifyExistsEmail(String email) {
        User user = userRepository.findByEmail(email);

        if(user != null)
            throw new BusinessException(ExceptionCode.USER_EXISTS);
    }

    // (2) user 정보 조회
    public User findUser(long userId) {
        return findVerifiedUser(userId);
    }

    // 이미 존재하는 유저인지 검증
    public User findVerifiedUser(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User findUser = optionalUser.orElseThrow(() ->
                new BusinessException(ExceptionCode.USER_NOT_FOUND));

        return findUser;
    }

    // (3) user 정보 수정
    public User updateUser(User user) {

        // 해당 회원의 아이디를 가져옴
        User findUser = findVerifiedUser(user.getUserId());

        // 회원정보 업데이트
        Optional.ofNullable(user.getUserName())
                .ifPresent(name -> findUser.setUserName(name));
        Optional.ofNullable(user.getPassword())
                .ifPresent(password -> findUser.setPassword(password));
        Optional.of(user.getPhoneNumber())
                .ifPresent(phoneNumber -> findUser.setPhoneNumber(phoneNumber));
        Optional.ofNullable(user.getAddress())
                .ifPresent(address -> findUser.setAddress(address));

        // 회원정보 수정 저장
        return userRepository.save(findUser);
    }



}
