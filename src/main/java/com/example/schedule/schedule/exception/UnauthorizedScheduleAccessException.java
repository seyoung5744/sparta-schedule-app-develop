package com.example.schedule.schedule.exception;

import com.example.schedule.common.exception.ErrorCode;
import com.example.schedule.common.exception.GlobalException;

public class UnauthorizedScheduleAccessException extends GlobalException {

    public UnauthorizedScheduleAccessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
