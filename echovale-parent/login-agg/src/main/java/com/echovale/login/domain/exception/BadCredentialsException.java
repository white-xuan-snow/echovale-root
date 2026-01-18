package com.echovale.login.domain.exception;

import com.echovale.login.infrastructure.properties.LoginStrategyProperties;
import com.echovale.shared.utils.StringUtil;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/18 16:30
 */
public class BadCredentialsException extends BaseLoginException {
    public BadCredentialsException(String identifier) {
        super(LoginStrategyProperties.PASSWORD_UNAUTHORIZED_MESSAGE, identifier);
    }
}
