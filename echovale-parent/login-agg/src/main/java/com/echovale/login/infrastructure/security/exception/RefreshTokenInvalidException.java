package com.echovale.login.infrastructure.security.exception;

import com.echovale.login.domain.entity.AuthErrorCode;

public class RefreshTokenInvalidException extends BaseAuthException {
    public RefreshTokenInvalidException(Throwable throwable) { super(AuthErrorCode.REFRESH_TOKEN_INVALID, throwable); }
    public RefreshTokenInvalidException() { super(AuthErrorCode.REFRESH_TOKEN_INVALID); }
}
