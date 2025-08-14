package com.example.schedule.domain.auth.dto.response;

import com.example.schedule.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthInfoResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static AuthInfoResponse of(User user) {
        return new AuthInfoResponse(user.getId(), user.getName(), user.getEmail(), user.getCreateAt(), user.getModifiedAt());
    }
}
