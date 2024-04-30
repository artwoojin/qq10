package com.project.qq10.domain.controller;

import com.project.qq10.global.security.UserDetailsImpl;
import com.project.qq10.domain.dto.LoginRequestDto;
import com.project.qq10.domain.dto.SignupRequestDto;
import com.project.qq10.domain.dto.UpdateProfileRequestDto;
import com.project.qq10.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    @GetMapping("/user/signup")
//    public String signupPage() {
//        return "signup";
//    }

    @PostMapping("/user/signup")
    public String signup(@RequestBody @Validated SignupRequestDto requestDto) {

        return userService.signup(requestDto);
    }
    @PostMapping("/user/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto) {
        return "login success";
    }

    @PutMapping("/user/profile")
    public String updateProfile(@RequestBody @Validated UpdateProfileRequestDto updateProfileRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.updateProfile(updateProfileRequestDto, userDetails.getUser());
    }
}
