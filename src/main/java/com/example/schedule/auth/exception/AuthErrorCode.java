package com.example.schedule.auth.exception;

import com.example.schedule.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    FORBIDDEN_USER_ACCESS(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "비밀번호가 올바르지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
