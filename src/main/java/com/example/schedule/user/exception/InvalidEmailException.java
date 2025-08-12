package com.example.schedule.user.exception;

import com.example.schedule.common.exception.ErrorCode;
import com.example.schedule.common.exception.GlobalException;

public class InvalidEmailException extends GlobalException {

    public InvalidEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
