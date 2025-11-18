package com.echovale.common.domain.api.exception;


import com.echovale.common.domain.api.constant.ErrorCode;
import com.echovale.common.domain.api.constant.ErrorMessage;

public class InternalServerErrorException extends BaseException {
    public InternalServerErrorException() { super(ErrorCode.INTERNAL_SERVER_ERROR_CODE, ErrorMessage.INTERNAL_SERVER_ERROR_MESSAGE); }
    public InternalServerErrorException(String msg) { super(ErrorCode.INTERNAL_SERVER_ERROR_CODE, msg + ErrorMessage.INTERNAL_SERVER_ERROR_MESSAGE); }
    public InternalServerErrorException(String msg, Throwable throwable) { super(ErrorCode.INTERNAL_SERVER_ERROR_CODE, msg + ErrorMessage.INTERNAL_SERVER_ERROR_MESSAGE + throwable.getLocalizedMessage(), throwable); }
}
