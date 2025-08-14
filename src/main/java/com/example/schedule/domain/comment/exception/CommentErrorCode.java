package com.example.schedule.domain.comment.exception;

import com.example.schedule.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode {

    INVALID_COMMENT(HttpStatus.BAD_REQUEST, "존재하지 않은 댓글입니다."),
    UNAUTHORIZED_COMMENT_ACCESS(HttpStatus.FORBIDDEN, "댓글에 접근 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
