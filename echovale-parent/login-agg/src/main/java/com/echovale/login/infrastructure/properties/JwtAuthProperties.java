package com.echovale.login.infrastructure.properties;

import com.echovale.shared.infrastructure.properties.AbstractInitProperties;
import com.echovale.shared.infrastructure.properties.ProjectProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/28 18:31
 */

@Component
@ConfigurationProperties("login.security.jwt")
@Data
public class JwtAuthProperties extends AbstractInitProperties {
    private String secret;
    private Duration accessExpiration;
    private Duration refreshExpiration;
    private String issuer;

    public static String SECRET;
    public static Duration ACCESS_EXPIRATION;
    public static Duration REFRESH_EXPIRATION;
    public static String ISSUER;

    private Duration jwtJtiExpire;
    private String jwtJtiPrefix;

    public static String JWT_JTI_KEY_FORMAT;
    public static Duration JWT_JTI_EXPIRE;
    public static String JWT_JTI_PREFIX;


    private String accessTokenExpiredMessage;
    private String refreshTokenExpiredMessage;
    private String accessTokenInvalidMessage;
    private String refreshTokenInvalidMessage;

    public static String ACCESS_TOKEN_EXPIRED_MESSAGE = "Access Token 已过期";
    public static String REFRESH_TOKEN_EXPIRED_MESSAGE = "Refresh Token 已过期";
    public static String ACCESS_TOKEN_INVALID_MESSAGE = "Access Token 无效";
    public static String REFRESH_TOKEN_INVALID_MESSAGE = "Refresh Token 无效";


    @Override
    protected void manualSyncCustomStaticFields() {
        JWT_JTI_KEY_FORMAT = ProjectProperties.NAME + ":" +
                AuthProperties.NAME + ":" +
                JWT_JTI_PREFIX + ":" +
                "%s";
    }
}
