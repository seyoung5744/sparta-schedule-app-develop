package com.example.schedule.domain.comment.dto.response;

import com.example.schedule.domain.comment.entity.Comment;
import com.example.schedule.domain.schedule.entity.Schedule;
import com.example.schedule.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentListResponse {

    private final ScheduleResponse schedule;
    private final List<CommentResponse> comments;

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
    @Builder(access = AccessLevel.PRIVATE)
    private static class ScheduleResponse {
        private final Long id;
        private final String title;
        private final String contents;
        private final int commentCount;
        private final LocalDateTime createdAt;
        private final LocalDateTime modifiedAt;
        private final WriterResponse writer;

        public static ScheduleResponse of(Schedule schedule) {
            return ScheduleResponse.builder()
                    .id(schedule.getId())
                    .title(schedule.getTitle())
                    .contents(schedule.getContents())
                    .commentCount(schedule.getComments().size())
                    .createdAt(schedule.getCreateAt())
                    .modifiedAt(schedule.getModifiedAt())
                    .writer(WriterResponse.of(schedule.getUser()))
                    .build();
        }
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static class CommentResponse {
        private final Long id;
        private final String contents;
        private final WriterResponse writer;
        private final LocalDateTime createdAt;
        private final LocalDateTime modifiedAt;

        public static CommentResponse of(Comment comment) {
            return new CommentResponse(comment.getId(), comment.getContents(), WriterResponse.of(comment.getUser()), comment.getCreateAt(), comment.getModifiedAt());
        }
    }

    public static CommentListResponse of(Schedule schedule, List<Comment> comments) {
        return new CommentListResponse(
                ScheduleResponse.of(schedule),
                comments.stream()
                        .map(CommentResponse::of)
                        .toList()
        );
    }
}
