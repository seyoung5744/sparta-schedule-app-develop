package com.example.schedule.user.exception;

import com.example.schedule.common.exception.ErrorCode;
import com.example.schedule.common.exception.GlobalException;

public class DuplicationEmailException extends GlobalException {

    public DuplicationEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
