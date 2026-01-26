package com.echovale.shared.domain.exception;

import com.echovale.shared.domain.constant.ErrorCode;
import com.echovale.shared.domain.constant.ErrorMessage;

public class NotImplementedException extends BaseException {
    public NotImplementedException() { super(ErrorCode.NOT_IMPLEMENTED_CODE, ErrorMessage.NOT_IMPLEMENTED_MESSAGE); }
    public NotImplementedException(String msg) { super(ErrorCode.NOT_IMPLEMENTED_CODE, msg + ErrorMessage.NOT_IMPLEMENTED_MESSAGE); }
    public NotImplementedException(String msg, Throwable throwable) { super(ErrorCode.NOT_IMPLEMENTED_CODE, msg + ErrorMessage.NOT_IMPLEMENTED_MESSAGE + throwable.getLocalizedMessage(), throwable); }
}
