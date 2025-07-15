package com.echovale.common.exception;

import com.echovale.common.constants.code.ErrorCode;
import com.echovale.common.constants.message.ErrorMessage;

public class ForbiddenException extends BaseException {
    public ForbiddenException() { super(ErrorCode.ForbiddenCode, ErrorMessage.ForbiddenMessage); }
    public ForbiddenException(String msg) { super(ErrorCode.ForbiddenCode, msg + ErrorMessage.ForbiddenMessage); }
    public ForbiddenException(String msg, Throwable throwable) { super(ErrorCode.ForbiddenCode, msg + ErrorMessage.ForbiddenMessage + throwable.getLocalizedMessage(), throwable); }
}
