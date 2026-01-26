package com.echovale.shared.domain.exception;

import com.echovale.shared.domain.constant.ErrorCode;
import com.echovale.shared.domain.constant.ErrorMessage;

public class NotFoundException extends BaseException {
    public NotFoundException() { super(ErrorCode.NOT_FOUND_CODE, ErrorMessage.NOT_FOUND_MESSAGE); }
    public NotFoundException(String msg) { super(ErrorCode.NOT_FOUND_CODE, msg + ErrorMessage.NOT_FOUND_MESSAGE); }
    public NotFoundException(String msg, Throwable throwable) { super(ErrorCode.NOT_FOUND_CODE, msg + ErrorMessage.NOT_FOUND_MESSAGE + throwable.getLocalizedMessage(), throwable); }
}
