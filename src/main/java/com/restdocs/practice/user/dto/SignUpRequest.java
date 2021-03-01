package com.restdocs.practice.user.dto;

import com.restdocs.practice.user.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"email", "fullName", "password"})
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
