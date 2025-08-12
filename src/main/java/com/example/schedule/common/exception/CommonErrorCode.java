package com.example.schedule.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    INVALID_USER(HttpStatus.BAD_REQUEST, "존재하지 않은 회원입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;

}
