package com.example.schedule.comment.exception;

import com.example.schedule.common.exception.ErrorCode;
import com.example.schedule.common.exception.GlobalException;

public class UnauthorizedCommentAccessException extends GlobalException {

    public UnauthorizedCommentAccessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
