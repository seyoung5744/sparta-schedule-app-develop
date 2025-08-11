package com.example.schedule.user.dto.response;

import com.example.schedule.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfoResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static UserInfoResponse of(User user) {
        return new UserInfoResponse(user.getId(), user.getName(), user.getEmail(), user.getCreateAt(), user.getModifiedAt());
    }
}
