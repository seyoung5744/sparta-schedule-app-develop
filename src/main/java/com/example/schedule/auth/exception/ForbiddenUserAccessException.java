package com.example.schedule.auth.exception;

import com.example.schedule.common.exception.ErrorCode;
import com.example.schedule.common.exception.GlobalException;

public class ForbiddenUserAccessException extends GlobalException {

    public ForbiddenUserAccessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
