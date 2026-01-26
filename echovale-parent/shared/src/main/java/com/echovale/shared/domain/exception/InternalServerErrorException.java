package com.echovale.shared.domain.exception;


import com.echovale.shared.domain.constant.ErrorCode;
import com.echovale.shared.domain.constant.ErrorMessage;

public class InternalServerErrorException extends BaseException {
    public InternalServerErrorException() { super(ErrorCode.INTERNAL_SERVER_ERROR_CODE, ErrorMessage.INTERNAL_SERVER_ERROR_MESSAGE); }
    public InternalServerErrorException(String msg) { super(ErrorCode.INTERNAL_SERVER_ERROR_CODE, msg + ErrorMessage.INTERNAL_SERVER_ERROR_MESSAGE); }
    public InternalServerErrorException(String msg, Throwable throwable) { super(ErrorCode.INTERNAL_SERVER_ERROR_CODE, msg + ErrorMessage.INTERNAL_SERVER_ERROR_MESSAGE + throwable.getLocalizedMessage(), throwable); }
}
