package com.echovale.login.infrastructure.security.exception;

import com.echovale.login.domain.entity.AuthErrorCode;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/2/1 20:30
 */
public class SecondaryCaptchaFailedException extends BaseAuthException {
    public SecondaryCaptchaFailedException() {
        super(AuthErrorCode.SECONDARY_CAPTCHA_FAILED);
    }
    public SecondaryCaptchaFailedException(Throwable throwable) {
        super(AuthErrorCode.SECONDARY_CAPTCHA_FAILED, throwable);
    }
}
