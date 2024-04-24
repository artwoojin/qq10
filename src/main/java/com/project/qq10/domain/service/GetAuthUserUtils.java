package com.project.qq10.domain.service;


import com.project.qq10.domain.exception.BusinessException;
import com.project.qq10.domain.exception.ExceptionCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class GetAuthUserUtils {
    public static Authentication getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getName() == null || authentication.getName().equals("anonymousUser")){
            throw new BusinessException(ExceptionCode.USER_NOT_FOUND);
        }
        authentication.getPrincipal();
        return authentication;
    }
}
