package com.example.schedule.schedule.dto.response;

import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponse {

    private final Long id;
    private final WriterResponse writer;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    @Builder
    private ScheduleResponse(Long id, WriterResponse writer, String title, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static class WriterResponse {
        private final Long id;
        private final String name;

        public static WriterResponse of(User user) {
            return new WriterResponse(user.getId(), user.getName());
        }
    }

    public static ScheduleResponse of(Schedule schedule) {
        return ScheduleResponse.builder()
                .id(schedule.getId())
                .writer(WriterResponse.of(schedule.getUser()))
                .title(schedule.getTitle())
                .contents(schedule.getContents())
                .createdAt(schedule.getCreateAt())
                .modifiedAt(schedule.getModifiedAt())
                .build();
    }
}
