package com.echovale.login.infrastructure.security.exception;

import com.echovale.login.domain.entity.AuthErrorCode;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/2/1 19:37
 */
public class AccessTokenExpiredException extends BaseAuthException {
    public AccessTokenExpiredException(Throwable throwable) { super(AuthErrorCode.ACCESS_TOKEN_EXPIRED, throwable); }
    public AccessTokenExpiredException() { super(AuthErrorCode.ACCESS_TOKEN_EXPIRED); }
}
