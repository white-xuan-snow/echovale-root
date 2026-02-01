package com.echovale.login.infrastructure.security.exception;

import com.echovale.login.domain.entity.AuthErrorCode;

public class AccessTokenInvalidException extends BaseAuthException {
    public AccessTokenInvalidException(Throwable throwable) { super(AuthErrorCode.ACCESS_TOKEN_INVALID, throwable); }
    public AccessTokenInvalidException() { super(AuthErrorCode.ACCESS_TOKEN_INVALID); }
}
