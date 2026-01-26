package com.echovale.shared.domain.exception;


import com.echovale.shared.domain.constant.ErrorCode;
import com.echovale.shared.domain.constant.ErrorMessage;

public class ConflictException extends BaseException {
    public ConflictException() { super(ErrorCode.CONFLICT_CODE, ErrorMessage.CONFLICT_MESSAGE); }
    public ConflictException(String msg) { super(ErrorCode.CONFLICT_CODE, msg + ErrorMessage.CONFLICT_MESSAGE); }
    public ConflictException(String msg, Throwable throwable) { super(ErrorCode.CONFLICT_CODE, msg + ErrorMessage.CONFLICT_MESSAGE + throwable.getLocalizedMessage(), throwable); }
}
