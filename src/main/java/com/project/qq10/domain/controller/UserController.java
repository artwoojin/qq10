package com.project.qq10.domain.controller;

import com.project.qq10.domain.dto.UserDto;
import com.project.qq10.domain.entity.User;
import com.project.qq10.domain.mapper.UserMapper;
import com.project.qq10.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;

    // user 등록(회원 가입)
    @PostMapping("/sign-up")
    public ResponseEntity postUser(@Valid @RequestBody UserDto.Post requestBody) {

        User user = mapper.userPostToUser(requestBody);
        User createUser = userService.createUser(user);

        return new ResponseEntity<>(mapper.userToUserResponse(createUser), HttpStatus.CREATED);
    }
}
