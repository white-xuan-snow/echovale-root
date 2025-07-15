package com.echovale.common.exception;

import com.echovale.common.constants.code.ErrorCode;
import com.echovale.common.constants.message.ErrorMessage;

public class BadRequestException extends BaseException {
    public BadRequestException() { super(ErrorCode.BadRequestCode, ErrorMessage.BadRequestMessage); }
    public BadRequestException(String msg) { super(ErrorCode.BadRequestCode, msg + ErrorMessage.BadRequestMessage); }
    public BadRequestException(String msg, Throwable throwable) { super(ErrorCode.BadRequestCode, msg + ErrorMessage.BadRequestMessage + throwable.getLocalizedMessage(), throwable); }
}
