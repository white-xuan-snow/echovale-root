package com.echovale.shared.domain.exception;

import com.echovale.shared.domain.constant.ErrorCode;
import com.echovale.shared.domain.constant.ErrorMessage;

public class MethodNotAllowedException extends BaseException {
    public MethodNotAllowedException() { super(ErrorCode.METHOD_NOT_ALLOWED_CODE, ErrorMessage.METHOD_NOT_ALLOWED_MESSAGE); }
    public MethodNotAllowedException(String msg) { super(ErrorCode.METHOD_NOT_ALLOWED_CODE, msg + ErrorMessage.METHOD_NOT_ALLOWED_MESSAGE); }
    public MethodNotAllowedException(String msg, Throwable throwable) { super(ErrorCode.METHOD_NOT_ALLOWED_CODE, msg + ErrorMessage.METHOD_NOT_ALLOWED_MESSAGE + throwable.getLocalizedMessage(), throwable); }
}
