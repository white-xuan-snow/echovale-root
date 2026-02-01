package com.echovale.shared.domain.exception;

import com.echovale.shared.domain.constant.ErrorCode;
import com.echovale.shared.domain.constant.ErrorMessage;
import com.echovale.shared.infrastructure.utils.StringUtil;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException() { super(ErrorCode.UNAUTHORIZED_CODE, ErrorMessage.UNAUTHORIZED_MESSAGE); }
    public UnauthorizedException(String msg, Throwable throwable) { super(ErrorCode.UNAUTHORIZED_CODE, msg + ErrorMessage.UNAUTHORIZED_MESSAGE + throwable.getLocalizedMessage(), throwable); }
    public UnauthorizedException(String msg) { super(ErrorCode.UNAUTHORIZED_CODE, StringUtil.oneOf(msg, ErrorMessage.UNAUTHORIZED_MESSAGE)); }
    public UnauthorizedException(Integer code, String msg) { super(code, StringUtil.oneOf(msg, ErrorMessage.UNAUTHORIZED_MESSAGE)); }

}
