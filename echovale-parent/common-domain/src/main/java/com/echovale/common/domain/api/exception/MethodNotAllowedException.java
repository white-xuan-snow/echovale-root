package com.echovale.common.domain.api.exception;

import com.echovale.common.domain.api.constant.ErrorCode;
import com.echovale.common.domain.api.constant.ErrorMessage;

public class MethodNotAllowedException extends BaseException {
    public MethodNotAllowedException() { super(ErrorCode.METHOD_NOT_ALLOWED_CODE, ErrorMessage.METHOD_NOT_ALLOWED_MESSAGE); }
    public MethodNotAllowedException(String msg) { super(ErrorCode.METHOD_NOT_ALLOWED_CODE, msg + ErrorMessage.METHOD_NOT_ALLOWED_MESSAGE); }
    public MethodNotAllowedException(String msg, Throwable throwable) { super(ErrorCode.METHOD_NOT_ALLOWED_CODE, msg + ErrorMessage.METHOD_NOT_ALLOWED_MESSAGE + throwable.getLocalizedMessage(), throwable); }
}
