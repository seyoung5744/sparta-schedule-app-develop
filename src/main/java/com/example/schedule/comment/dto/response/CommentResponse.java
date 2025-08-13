package com.example.schedule.comment.dto.response;

import com.example.schedule.comment.entity.Comment;
import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentResponse {

    private final Long id;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final WriterResponse writer;
    private final ScheduleResponse schedule;

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static class WriterResponse {
        private final Long id;
        private final String name;

        public static WriterResponse of(User user) {
            return new WriterResponse(user.getId(), user.getName());
        }
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static class ScheduleResponse {
        private final Long id;
        private final String title;
        private final String contents;
        private final int commentCount;
        private final LocalDateTime createdAt;
        private final LocalDateTime modifiedAt;
        private final WriterResponse writer;

        public static ScheduleResponse of(Schedule schedule) {
            return new ScheduleResponse(schedule.getId(), schedule.getTitle(), schedule.getContents(),
                    schedule.getComments().size(), schedule.getCreateAt(), schedule.getModifiedAt(), WriterResponse.of(schedule.getUser()));
        }
    }

    public static CommentResponse of(Comment comment, Schedule schedule) {
        return new CommentResponse(
                comment.getId(),
                comment.getContents(),
                comment.getCreateAt(),
                comment.getModifiedAt(),
                WriterResponse.of(comment.getUser()),
                ScheduleResponse.of(schedule)
        );
    }
}
