package com.example.schedule.schedule.dto.response;

import com.example.schedule.schedule.entity.Schedule;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponse {

    private final Long id;
    private final String writer;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    @Builder
    private ScheduleResponse(Long id, String writer, String title, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static ScheduleResponse of(Schedule schedule) {
        return ScheduleResponse.builder()
                .id(schedule.getId())
                .writer(schedule.getWriter())
                .title(schedule.getTitle())
                .contents(schedule.getContents())
                .createdAt(schedule.getCreateAt())
                .modifiedAt(schedule.getModifiedAt())
                .build();
    }
}
