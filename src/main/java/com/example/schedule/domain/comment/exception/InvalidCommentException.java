package com.example.schedule.domain.comment.exception;

import com.example.schedule.common.exception.ErrorCode;
import com.example.schedule.common.exception.GlobalException;

public class InvalidCommentException extends GlobalException {

    public InvalidCommentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
