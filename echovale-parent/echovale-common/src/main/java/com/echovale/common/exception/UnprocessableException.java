package com.echovale.common.exception;

import com.echovale.common.constants.code.ErrorCode;
import com.echovale.common.constants.message.ErrorMessage;

public class UnprocessableException extends BaseException {
    public UnprocessableException() { super(ErrorCode.UnprocessableCode, ErrorMessage.UnprocessableMessage); }
    public UnprocessableException(String msg) { super(ErrorCode.UnprocessableCode, msg + ErrorMessage.UnprocessableMessage); }
    public UnprocessableException(String msg, Throwable throwable) { super(ErrorCode.UnprocessableCode, msg + ErrorMessage.UnprocessableMessage + throwable.getLocalizedMessage(), throwable); }
}
