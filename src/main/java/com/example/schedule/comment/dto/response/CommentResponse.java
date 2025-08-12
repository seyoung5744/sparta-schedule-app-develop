package com.example.schedule.comment.dto.response;

import com.example.schedule.comment.entity.Comment;
import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentResponse {

    private final Long id;
    private final String contents;
    private final WriterResponse writer;
    private final ScheduleResponse schedule;

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static class WriterResponse {
        private final Long id;
        private final String name;
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static class ScheduleResponse {
        private final Long id;
        private final String title;
        private final String contents;
    }

    public static CommentResponse of(Comment comment, User user, Schedule schedule) {
        return new CommentResponse(
                comment.getId(),
                comment.getContents(),
                new WriterResponse(user.getId(), user.getName()),
                new ScheduleResponse(schedule.getId(), schedule.getTitle(), schedule.getContents())
        );
    }
}
