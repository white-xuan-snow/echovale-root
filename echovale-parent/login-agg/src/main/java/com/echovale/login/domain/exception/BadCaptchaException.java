package com.echovale.login.domain.exception;

import com.echovale.login.domain.entity.AuthErrorCode;
import com.echovale.login.infrastructure.properties.LoginStrategyProperties;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/18 16:32
 */


public class BadCaptchaException extends BaseLoginException {
    public BadCaptchaException(String identifier) {
        super(AuthErrorCode.BAD_CAPTCHA, identifier);
    }
}
