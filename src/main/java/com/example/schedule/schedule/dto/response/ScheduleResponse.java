package com.example.schedule.schedule.dto.response;

import com.example.schedule.schedule.entity.Schedule;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ScheduleResponse {

    private final Long id;
    private final String writer;
    private final String title;
    private final String contents;

    public static ScheduleResponse of(Schedule schedule) {
        return new ScheduleResponse(
                schedule.getId(),
                schedule.getWriter(),
                schedule.getTitle(),
                schedule.getContents()
        );
    }
}
