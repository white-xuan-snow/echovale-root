package com.echovale.common.exception;

import com.echovale.common.constants.code.ErrorCode;
import com.echovale.common.constants.message.ErrorMessage;

public class GoneException extends BaseException {
    public GoneException() {
        super(ErrorCode.GoneCode, ErrorMessage.GoneMessages);
    }
    public GoneException(String msg) {
        super(ErrorCode.GoneCode, msg + ErrorMessage.GoneMessages);
    }
    public GoneException(String msg, Throwable throwable) { super(ErrorCode.GoneCode, msg + ErrorMessage.GoneMessages + throwable.getLocalizedMessage(), throwable); }
}
