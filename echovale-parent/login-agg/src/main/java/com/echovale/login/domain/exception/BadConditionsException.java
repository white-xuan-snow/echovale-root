package com.echovale.login.domain.exception;

import com.echovale.login.domain.entity.AuthErrorCode;
import com.echovale.login.infrastructure.properties.LoginStrategyProperties;

public class BadConditionsException extends BaseLoginException {
    public BadConditionsException(String identifier) {
        super(AuthErrorCode.BAD_CONDITIONS, identifier);
    }
}
