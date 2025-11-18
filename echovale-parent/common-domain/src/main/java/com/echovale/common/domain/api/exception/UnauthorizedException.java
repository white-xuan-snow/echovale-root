package com.echovale.common.domain.api.exception;

import com.echovale.common.domain.api.constant.ErrorCode;
import com.echovale.common.domain.api.constant.ErrorMessage;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException() { super(ErrorCode.UNAUTHORIZED_CODE, ErrorMessage.UNAUTHORIZED_MESSAGE); }
    public UnauthorizedException(String msg, Throwable throwable) { super(ErrorCode.UNAUTHORIZED_CODE, msg + ErrorMessage.UNAUTHORIZED_MESSAGE + throwable.getLocalizedMessage(), throwable); }
    public UnauthorizedException(String msg) { super(ErrorCode.UNAUTHORIZED_CODE, msg + ErrorMessage.UNAUTHORIZED_MESSAGE); }
}
