package com.echovale.login.infrastructure.properties;

import com.echovale.shared.properties.ProjectProperties;
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

    private Duration loginRecordExpire;
    private String loginRecordPrefix;
    private Integer loginIpRecordMaxTimes;
    private Integer loginIdRecordMaxTimes;


    private Duration loginLockedExpire;
    private String loginLockedPrefix;


    public static Duration REFRESH_TOKEN_EXPIRE;
    public static String REFRESH_TOKEN_PREFIX;
    public static String REFRESH_TOKEN_KEY_FORMAT;

    public static String LOGIN_RECORD_KEY_FORMAT;
    public static String LOGIN_RECORD_PREFIX;
    public static Duration LOGIN_RECORD_EXPIRE;
    public static Integer LOGIN_IP_RECORD_MAX_TIMES;
    public static Integer LOGIN_ID_RECORD_MAX_TIMES;


    public static Duration LOGIN_LOCKED_EXPIRE;
    public static String LOGIN_LOCKED_PREFIX;
    public static String LOGIN_LOCKED_KEY_FORMAT;



    @PostConstruct
    private void init() {
        REFRESH_TOKEN_EXPIRE = this.refreshTokenExpire;
        REFRESH_TOKEN_PREFIX = this.refreshTokenPrefix;
        REFRESH_TOKEN_KEY_FORMAT =
                ProjectProperties.NAME + ":" +
                "login:" +
                refreshTokenPrefix + ":" +
                "%s";

        LOGIN_RECORD_EXPIRE = this.loginRecordExpire;
        LOGIN_RECORD_PREFIX = this.loginRecordPrefix;
        LOGIN_ID_RECORD_MAX_TIMES = this.loginIdRecordMaxTimes;
        LOGIN_IP_RECORD_MAX_TIMES = this.loginIpRecordMaxTimes;
        LOGIN_RECORD_KEY_FORMAT =
                ProjectProperties.NAME + ":" +
                "login:" +
                loginRecordPrefix + ":" +
                "%s";

        LOGIN_LOCKED_EXPIRE = this.loginLockedExpire;
        LOGIN_LOCKED_PREFIX = this.loginLockedPrefix;
        LOGIN_LOCKED_KEY_FORMAT =
                ProjectProperties.NAME + ":" +
                "login:" +
                loginLockedPrefix + ":" +
                "%s";
    }

}
