package com.echovale.common.domain.api.exception;


import com.echovale.common.domain.api.constant.ErrorCode;
import com.echovale.common.domain.api.constant.ErrorMessage;

public class ConflictException extends BaseException {
    public ConflictException() { super(ErrorCode.CONFLICT_CODE, ErrorMessage.CONFLICT_MESSAGE); }
    public ConflictException(String msg) { super(ErrorCode.CONFLICT_CODE, msg + ErrorMessage.CONFLICT_MESSAGE); }
    public ConflictException(String msg, Throwable throwable) { super(ErrorCode.CONFLICT_CODE, msg + ErrorMessage.CONFLICT_MESSAGE + throwable.getLocalizedMessage(), throwable); }
}
