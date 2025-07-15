package com.echovale.common.exception;

import com.echovale.common.constants.code.ErrorCode;
import com.echovale.common.constants.message.ErrorMessage;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException() { super(ErrorCode.UnauthorizedCode, ErrorMessage.UnauthorizedMessage); }
    public UnauthorizedException(String msg) { super(ErrorCode.UnauthorizedCode, msg + ErrorMessage.UnauthorizedMessage); }
    public UnauthorizedException(String msg, Throwable throwable) { super(ErrorCode.UnauthorizedCode, msg + ErrorMessage.UnauthorizedMessage + throwable.getLocalizedMessage(), throwable); }
}
