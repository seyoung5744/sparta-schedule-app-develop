package com.example.schedule.user.exception;

import com.example.schedule.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    INVALID_USER(HttpStatus.BAD_REQUEST, "존재하지 않은 회원입니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
