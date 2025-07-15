package com.echovale.common.exception;

import com.echovale.common.constants.code.ErrorCode;
import com.echovale.common.constants.message.ErrorMessage;

public class MethodNotAllowedException extends BaseException {
    public MethodNotAllowedException() { super(ErrorCode.MethodNotAllowedCode, ErrorMessage.MethodNotAllowedMessage); }
    public MethodNotAllowedException(String msg) { super(ErrorCode.MethodNotAllowedCode, msg + ErrorMessage.MethodNotAllowedMessage); }
    public MethodNotAllowedException(String msg, Throwable throwable) { super(ErrorCode.MethodNotAllowedCode, msg + ErrorMessage.MethodNotAllowedMessage + throwable.getLocalizedMessage(), throwable); }
}
