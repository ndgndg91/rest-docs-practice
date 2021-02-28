package com.restdocs.practice.user.dto;

import com.restdocs.practice.user.User;
import lombok.Getter;

@Getter
public final class UserResponse {

    private final long id;
    private final String email;
    private final String fullName;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
    }
}
