package com.example.schedule.domain.schedule.exception;

import com.example.schedule.common.exception.ErrorCode;
import com.example.schedule.common.exception.GlobalException;

public class InvalidScheduleException extends GlobalException {

    public InvalidScheduleException(ErrorCode errorCode) {
        super(errorCode);
    }
}
