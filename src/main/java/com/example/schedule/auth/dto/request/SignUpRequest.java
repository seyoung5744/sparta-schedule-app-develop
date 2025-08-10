package com.example.schedule.auth.dto.request;

import com.example.schedule.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpRequest {

    private final String name;
    private final String email;
    private final String password;

    public User toEntity() {
        return User.create(name, email, password);
    }
}
