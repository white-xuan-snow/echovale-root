package com.echovale.login.infrastructure.properties;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 22:20
 */

@Component
@ConfigurationProperties(prefix = "login.redis")
@Data
public class LoginRedisProperties {

    private Duration refreshTokenExpire;
    private String refreshTokenPrefix;


    public static Duration REFRESH_TOKEN_EXPIRE;
    public static String REFRESH_TOKEN_PREFIX;
    public static String REFRESH_TOKEN_KEY_FORMAT;

    @PostConstruct
    private void init() {
        REFRESH_TOKEN_EXPIRE = this.refreshTokenExpire;
        REFRESH_TOKEN_PREFIX = this.refreshTokenPrefix;
        REFRESH_TOKEN_KEY_FORMAT =
                "login:" +
                refreshTokenPrefix + ":" +
                "%s";
    }

}
