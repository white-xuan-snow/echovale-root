package com.echovale.shared.domain.exception;


import com.echovale.shared.domain.constant.ErrorCode;
import com.echovale.shared.domain.constant.ErrorMessage;

public class UnprocessableException extends BaseException {
    public UnprocessableException() { super(ErrorCode.UNPROCESSABLE_CODE, ErrorMessage.UNPROCESSABLE_MESSAGE); }
    public UnprocessableException(String msg) { super(ErrorCode.UNPROCESSABLE_CODE, msg + ErrorMessage.UNPROCESSABLE_MESSAGE); }
    public UnprocessableException(String msg, Throwable throwable) { super(ErrorCode.UNPROCESSABLE_CODE, msg + ErrorMessage.UNPROCESSABLE_MESSAGE + throwable.getLocalizedMessage(), throwable); }
}
