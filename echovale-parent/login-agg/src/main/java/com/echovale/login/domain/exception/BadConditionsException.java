package com.echovale.login.domain.exception;

import com.echovale.login.infrastructure.properties.LoginStrategyProperties;

public class BadConditionsException extends BaseLoginException {
    public BadConditionsException(String identifier) {
        super(LoginStrategyProperties.BAD_CONDITIONS_MESSAGE, identifier);
    }
}
