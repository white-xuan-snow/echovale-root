package com.echovale.common.domain.api.exception;

import com.echovale.common.domain.api.constant.ErrorCode;
import com.echovale.common.domain.api.constant.ErrorMessage;

public class NotImplementedException extends BaseException {
    public NotImplementedException() { super(ErrorCode.NOT_IMPLEMENTED_CODE, ErrorMessage.NOT_IMPLEMENTED_MESSAGE); }
    public NotImplementedException(String msg) { super(ErrorCode.NOT_IMPLEMENTED_CODE, msg + ErrorMessage.NOT_IMPLEMENTED_MESSAGE); }
    public NotImplementedException(String msg, Throwable throwable) { super(ErrorCode.NOT_IMPLEMENTED_CODE, msg + ErrorMessage.NOT_IMPLEMENTED_MESSAGE + throwable.getLocalizedMessage(), throwable); }
}
