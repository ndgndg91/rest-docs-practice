package com.restdocs.practice.user.dto;

import com.restdocs.practice.user.User;
import lombok.Getter;

@Getter
public final class SignUpRequest {

    private String email;
    private String fullName;
    private String password;

    public User toUser() {
        return User.builder()
                .email(email)
                .fullName(fullName)
                .password(password)
                .build();
    }
}
