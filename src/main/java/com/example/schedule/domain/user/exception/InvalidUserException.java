package com.example.schedule.domain.user.exception;

import com.example.schedule.common.exception.ErrorCode;
import com.example.schedule.common.exception.GlobalException;

public class InvalidUserException extends GlobalException {

    public InvalidUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
