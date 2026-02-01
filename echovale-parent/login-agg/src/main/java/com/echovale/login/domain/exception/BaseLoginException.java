package com.echovale.login.domain.exception;

import com.echovale.login.domain.entity.AuthErrorCode;
import lombok.Getter;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/18 16:28
 */

@Getter
public abstract class BaseLoginException extends RuntimeException {
    private final AuthErrorCode authErrorCode;
    private final String identifier;

    public BaseLoginException(AuthErrorCode authErrorCode, String identifier) {
        super(authErrorCode.getMsg());
        this.authErrorCode = authErrorCode;
        this.identifier = identifier;
    }
}