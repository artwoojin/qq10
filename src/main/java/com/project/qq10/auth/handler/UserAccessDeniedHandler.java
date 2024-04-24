package com.project.qq10.auth.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.qq10.auth.dto.ErrorResponseDto;
import com.project.qq10.auth.utils.ErrorResponder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
public class UserAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public UserAccessDeniedHandler() {
        objectMapper = null;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("No Authorities", accessDeniedException);

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.FORBIDDEN.value(),
                accessDeniedException.getMessage(), LocalDateTime.now());

        String responseBody = null;
        try {
            responseBody = objectMapper.writeValueAsString(errorResponseDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try {
            response.getWriter().write(responseBody);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setCharacterEncoding("UTF-8");
    }

}
