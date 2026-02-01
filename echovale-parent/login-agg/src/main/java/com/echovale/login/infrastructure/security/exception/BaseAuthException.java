package com.echovale.login.infrastructure.security.exception;

import com.echovale.login.domain.entity.AuthErrorCode;
import com.echovale.shared.domain.exception.BaseException;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/2/1 19:35
 */
public class BaseAuthException extends BaseException {
    public BaseAuthException(AuthErrorCode authErrorCode) { super(authErrorCode.getCode(), authErrorCode.getMsg()); }
    public BaseAuthException(AuthErrorCode authErrorCode, Throwable throwable) { super(authErrorCode.getCode(), authErrorCode.getMsg(), throwable); }
}
