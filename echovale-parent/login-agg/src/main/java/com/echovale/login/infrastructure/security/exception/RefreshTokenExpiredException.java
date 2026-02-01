package com.echovale.login.infrastructure.security.exception;

import com.echovale.login.domain.entity.AuthErrorCode;

public class RefreshTokenExpiredException extends BaseAuthException {
    public RefreshTokenExpiredException(Throwable throwable) { super(AuthErrorCode.REFRESH_TOKEN_EXPIRED, throwable); }
    public RefreshTokenExpiredException() { super(AuthErrorCode.REFRESH_TOKEN_EXPIRED); }
}
