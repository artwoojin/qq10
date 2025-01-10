package com.project.qq10.domain.controller;

import com.project.qq10.domain.dto.*;
import com.project.qq10.global.dto.ApiResponse;
import com.project.qq10.global.security.UserDetailsImpl;
import com.project.qq10.domain.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "user", description = "유저 관리")
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원 가입")
    @PostMapping("/user/signup")
    public String signup(@RequestBody @Validated SignupRequestDto requestDto) {

        return userService.signup(requestDto);
    }

    @Operation(summary = "로그인")
    @PostMapping("/user/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto) {
        return "login success";
    }

    @Operation(summary = "회원 정보 조회")
    @PutMapping("/user/profile")
    public ApiResponse<MyPageResponseDto> getMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        MyPageResponseDto MyPageResponseDto = userService.getMyPage(userDetails.getUser());

        return ApiResponse.success(MyPageResponseDto);
    }

    @Operation(summary = "비밀번호 변경")
    @PutMapping("/user/password")
    public ApiResponse<Boolean> updatePassword(@RequestBody @Validated PasswordChangeRequestDto password, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.updatePassword(password, userDetails.getUser());

        return ApiResponse.success();
    }

    @Operation(summary = "주소 변경")
    @PutMapping("/user/address")
    public ApiResponse<Boolean> updateAddress(@RequestParam @NotBlank String address, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.updateAddress(address, userDetails.getUser());

        return ApiResponse.success();
    }

    @Operation(summary = "휴대폰 번호 변경")
    @PutMapping("/user/phone")
    public ApiResponse<Boolean> updatePhoneNumber(@RequestParam @NotBlank String phoneNumber, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.updatePhoneNumber(phoneNumber, userDetails.getUser());

        return ApiResponse.success();
    }
}
