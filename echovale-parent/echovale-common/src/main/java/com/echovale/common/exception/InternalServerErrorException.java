package com.echovale.common.exception;

import com.echovale.common.constants.code.ErrorCode;
import com.echovale.common.constants.message.ErrorMessage;

public class InternalServerErrorException extends BaseException {
    public InternalServerErrorException() { super(ErrorCode.InternalServerErrorCode, ErrorMessage.InternalServerErrorMessage); }
    public InternalServerErrorException(String msg) { super(ErrorCode.InternalServerErrorCode, msg + ErrorMessage.InternalServerErrorMessage); }
    public InternalServerErrorException(String msg, Throwable throwable) { super(ErrorCode.InternalServerErrorCode, msg + ErrorMessage.InternalServerErrorMessage + throwable.getLocalizedMessage(), throwable); }
}
