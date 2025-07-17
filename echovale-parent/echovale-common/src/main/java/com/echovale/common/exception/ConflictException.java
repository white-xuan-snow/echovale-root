package com.echovale.common.exception;

import com.echovale.common.constants.code.ErrorCode;
import com.echovale.common.constants.message.ErrorMessage;

public class ConflictException extends BaseException {
    public ConflictException() { super(ErrorCode.ConflictCode, ErrorMessage.ConflictMessage); }
    public ConflictException(String msg) { super(ErrorCode.ConflictCode, msg + ErrorMessage.ConflictMessage); }
    public ConflictException(String msg, Throwable throwable) { super(ErrorCode.ConflictCode, msg + ErrorMessage.ConflictMessage + throwable.getLocalizedMessage(), throwable); }
}
