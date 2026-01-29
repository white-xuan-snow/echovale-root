package com.echovale.login.domain.exception;

import com.echovale.login.infrastructure.properties.LoginStrategyProperties;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/29 23:14
 */
public class BadRefreshTokenException extends BaseLoginException {
    public BadRefreshTokenException(String identifier) {
        super(LoginStrategyProperties.TOKEN_UNAUTHORIZED_MESSAGE, identifier);
    }
}
