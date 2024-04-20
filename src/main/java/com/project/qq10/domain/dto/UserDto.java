package com.project.qq10.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class UserDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Post {

        private long userId;

        @NotBlank(message = "이름은 공백이 아니어야 합니다.")
        private String userName;

        @NotBlank(message = "이메일은 공백이 아니어야 합니다.")
        @Email
        private String email;

        @NotBlank(message = "비밀번호는 공백이 아니어야 합니다.")
        private String password;

        private long phoneNumber;

        private String address;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Patch {

        private long userId;

        @NotBlank(message = "이름은 공백이 아니어야 합니다.")
        private String userName;

        @NotBlank(message = "비밀번호는 공백이 아니어야 합니다.")
        private String password;

        private long phoneNumber;

        private String address;

    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {

        private long userId;

        private String userName;
        private String email;
        private String password;
        private long phoneNumber;
        private String address;

    }
}

