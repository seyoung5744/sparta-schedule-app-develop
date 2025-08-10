package com.example.schedule.schedule.dto.request;

import com.example.schedule.schedule.entity.Schedule;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateScheduleRequest {

    private final String writer;
    private final String title;
    private final String contents;

    public Schedule toEntity() {
        return Schedule.create(writer, title, contents);
    }
}
