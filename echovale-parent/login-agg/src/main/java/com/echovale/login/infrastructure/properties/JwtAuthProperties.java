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


    @Override
    protected void manualSyncCustomStaticFields() {
        JWT_JTI_KEY_FORMAT = ProjectProperties.NAME + ":" +
                AuthProperties.NAME + ":" +
                JWT_JTI_PREFIX + ":" +
                "%s";
    }
}
