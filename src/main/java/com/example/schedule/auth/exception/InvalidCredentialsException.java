package com.example.schedule.auth.exception;

import com.example.schedule.common.exception.ErrorCode;
import com.example.schedule.common.exception.GlobalException;

public class InvalidCredentialsException extends GlobalException {

    public InvalidCredentialsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
