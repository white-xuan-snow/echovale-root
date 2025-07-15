package com.echovale.common.exception;

import com.echovale.common.constants.code.ErrorCode;
import com.echovale.common.constants.message.ErrorMessage;

public class NotImplementedException extends BaseException {
    public NotImplementedException() { super(ErrorCode.NotImplementedCode, ErrorMessage.NotImplementedMessage); }
    public NotImplementedException(String msg) { super(ErrorCode.NotImplementedCode, msg + ErrorMessage.NotImplementedMessage); }
    public NotImplementedException(String msg, Throwable throwable) { super(ErrorCode.NotImplementedCode, msg + ErrorMessage.NotImplementedMessage + throwable.getLocalizedMessage(), throwable); }
}
