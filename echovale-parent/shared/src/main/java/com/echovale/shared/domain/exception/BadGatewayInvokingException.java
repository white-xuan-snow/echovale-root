package com.echovale.shared.domain.exception;


import com.echovale.shared.domain.constant.ErrorCode;import com.echovale.shared.domain.constant.ErrorMessage;public class BadGatewayInvokingException extends BaseException {
    public BadGatewayInvokingException() { super(ErrorCode.BAD_REQUEST_CODE, ErrorMessage.BAD_REQUEST_MESSAGE); }
    public BadGatewayInvokingException(String msg) { super(ErrorCode.BAD_REQUEST_CODE, msg + ErrorMessage.BAD_REQUEST_MESSAGE); }
    public BadGatewayInvokingException(String msg, Throwable throwable) { super(ErrorCode.BAD_REQUEST_CODE, msg + ErrorMessage.BAD_REQUEST_MESSAGE + throwable.getLocalizedMessage(), throwable); }
}
