package com.echovale.common.domain.api.exception;


import com.echovale.common.domain.api.constant.ErrorCode;
import com.echovale.common.domain.api.constant.ErrorMessage;

public class ForbiddenException extends BaseException {
    public ForbiddenException() { super(ErrorCode.FORBIDDEN_CODE, ErrorMessage.FORBIDDEN_MESSAGE); }
    public ForbiddenException(String msg) { super(ErrorCode.FORBIDDEN_CODE, msg + ErrorMessage.FORBIDDEN_MESSAGE); }
    public ForbiddenException(String msg, Throwable throwable) { super(ErrorCode.FORBIDDEN_CODE, msg + ErrorMessage.FORBIDDEN_MESSAGE + throwable.getLocalizedMessage(), throwable); }
}
