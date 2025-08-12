package com.example.schedule.comment.dto.request;

import com.example.schedule.comment.entity.Comment;
import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateCommentRequest {

    @NotBlank(message = "댓글 내용은 필수입니다.")
    @Size(min = 1, max = 100, message = "댓글 내용은 최대 100자까지 입력 가능합니다.")
    private final String contents;

    public Comment toEntity(User user, Schedule schedule) {
        return Comment.create(user, schedule, contents);
    }
}
