package com.project.qq10.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.qq10.domain.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        User user = (User) authentication.getPrincipal();

        response.setCharacterEncoding("UTF-8");

        Map<String, Object> loginResponse = new HashMap<>();
        loginResponse.put("userId", user.getUserId());
        loginResponse.put("email", user.getEmail());
        loginResponse.put("username", user.getUserName());
        loginResponse.put("phoneNumber", user.getPhoneNumber());
        loginResponse.put("address", user.getAddress());

        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(loginResponse);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.getWriter().write(responseBody);

        log.info("# Authenticated successfully!");


    }
}
