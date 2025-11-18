package com.echovale.common.domain.api.exception;

import com.echovale.common.domain.api.constant.ErrorCode;
import com.echovale.common.domain.api.constant.ErrorMessage;

public class GoneException extends BaseException {
    public GoneException() {
        super(ErrorCode.GONE_CODE, ErrorMessage.GONE_MESSAGES);
    }
    public GoneException(String msg) {
        super(ErrorCode.GONE_CODE, msg + ErrorMessage.GONE_MESSAGES);
    }
    public GoneException(String msg, Throwable throwable) { super(ErrorCode.GONE_CODE, msg + ErrorMessage.GONE_MESSAGES + throwable.getLocalizedMessage(), throwable); }
}
