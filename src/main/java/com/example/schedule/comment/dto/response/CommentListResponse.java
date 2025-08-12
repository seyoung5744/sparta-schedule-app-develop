package com.example.schedule.comment.dto.response;

import com.example.schedule.comment.entity.Comment;
import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentListResponse {

    private final WriterResponse writer;
    private final ScheduleResponse schedule;
    private final List<CommentResponse> comments;

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

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static class CommentResponse {
        private final Long id;
        private final String contents;
        private final LocalDateTime createdAt;
        private final LocalDateTime modifiedAt;

        public static CommentResponse of(Comment comment) {
            return new CommentResponse(comment.getId(), comment.getContents(), comment.getCreateAt(), comment.getModifiedAt());
        }
    }

    public static CommentListResponse of(User user, Schedule schedule, List<Comment> comments) {
        return new CommentListResponse(
                new WriterResponse(user.getId(), user.getName()),
                new ScheduleResponse(schedule.getId(), schedule.getTitle(), schedule.getContents()),
                comments.stream()
                        .map(CommentResponse::of)
                        .toList()
        );
    }
}
