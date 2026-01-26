package com.echovale.shared.domain.exception;


import com.echovale.shared.domain.constant.ErrorCode;
import com.echovale.shared.domain.constant.ErrorMessage;

public class ForbiddenException extends BaseException {
    public ForbiddenException() { super(ErrorCode.FORBIDDEN_CODE, ErrorMessage.FORBIDDEN_MESSAGE); }
    public ForbiddenException(String msg) { super(ErrorCode.FORBIDDEN_CODE, msg + ErrorMessage.FORBIDDEN_MESSAGE); }
    public ForbiddenException(String msg, Throwable throwable) { super(ErrorCode.FORBIDDEN_CODE, msg + ErrorMessage.FORBIDDEN_MESSAGE + throwable.getLocalizedMessage(), throwable); }
}
