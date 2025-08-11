package com.example.schedule.schedule.dto.request;

import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateScheduleRequest {

    private final String title;
    private final String contents;

    public Schedule toEntity(User user) {
        return Schedule.create(user, title, contents);
    }
}
