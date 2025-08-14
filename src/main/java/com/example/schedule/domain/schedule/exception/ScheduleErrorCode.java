package com.example.schedule.domain.schedule.exception;

import com.example.schedule.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ScheduleErrorCode implements ErrorCode {

    INVALID_SCHEDULE(HttpStatus.NOT_FOUND, "존재하지 않은 일정입니다."),
    UNAUTHORIZED_SCHEDULE_ACCESS(HttpStatus.FORBIDDEN, "일정에 접근 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
