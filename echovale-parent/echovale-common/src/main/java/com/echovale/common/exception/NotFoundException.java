package com.echovale.common.exception;

import com.echovale.common.constants.code.ErrorCode;
import com.echovale.common.constants.message.ErrorMessage;

public class NotFoundException extends BaseException {
    public NotFoundException() { super(ErrorCode.NotFoundCode, ErrorMessage.NotFoundMessage); }
    public NotFoundException(String msg) { super(ErrorCode.NotFoundCode, msg + ErrorMessage.NotFoundMessage); }
    public NotFoundException(String msg, Throwable throwable) { super(ErrorCode.NotFoundCode, msg + ErrorMessage.NotFoundMessage + throwable.getLocalizedMessage(), throwable); }
}
