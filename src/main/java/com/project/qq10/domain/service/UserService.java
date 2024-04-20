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

    // user 등록(회원 가입)
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
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent())
            throw new BusinessException(ExceptionCode.USER_EXISTS);
    }

}
