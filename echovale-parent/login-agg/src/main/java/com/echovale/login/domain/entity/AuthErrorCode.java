package com.echovale.login.domain.entity;

import com.echovale.login.infrastructure.properties.JwtAuthProperties;
import com.echovale.login.infrastructure.properties.LoginStrategyProperties;
import com.echovale.shared.domain.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/2/1 20:02
 */

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    UNAUTHORIZED            (401001, "认证失败"),
    SECONDARY_CAPTCHA_FAILED(401002, LoginStrategyProperties.SECONDARY_CAPTCHA_UNAUTHORIZED_MESSAGE),
    BAD_CREDENTIALS         (401003, LoginStrategyProperties.PASSWORD_UNAUTHORIZED_MESSAGE),
    BAD_CAPTCHA             (401004, LoginStrategyProperties.CAPTCHA_UNAUTHORIZED_MESSAGE),
    BAD_CONDITIONS          (401005, LoginStrategyProperties.BAD_CONDITIONS_MESSAGE),
    ACCESS_TOKEN_EXPIRED    (401006, JwtAuthProperties.ACCESS_TOKEN_EXPIRED_MESSAGE),
    ACCESS_TOKEN_INVALID    (401007, JwtAuthProperties.ACCESS_TOKEN_INVALID_MESSAGE),
    REFRESH_TOKEN_EXPIRED   (401008, JwtAuthProperties.REFRESH_TOKEN_EXPIRED_MESSAGE),
    REFRESH_TOKEN_INVALID   (401009, JwtAuthProperties.REFRESH_TOKEN_INVALID_MESSAGE),
    ACCOUNT_STATUS_ERROR    (401010, LoginStrategyProperties.ACCOUNT_STATUS_ERROR_MESSAGE);

    private final int code;
    private final String msg;
}
