package com.echovale.login.infrastructure.properties;

import com.echovale.shared.infrastructure.properties.AbstractInitProperties;
import com.echovale.shared.infrastructure.properties.ProjectProperties;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 22:20
 */

@Slf4j
@Component
@ConfigurationProperties(prefix = "login.redis")
@Data
public class LoginRedisProperties extends AbstractInitProperties {

    private Duration refreshTokenExpire;
    private String refreshTokenPrefix;

    private Duration loginRecordExpire;
    private String loginRecordPrefix;
    private Integer loginIpRecordMaxTimes;
    private Integer loginIdRecordMaxTimes;


    private Duration loginLockedExpire;
    private String loginLockedPrefix;

    private Duration tokenRefreshThreshold;


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

    public static Duration TOKEN_REFRESH_THRESHOLD = Duration.ofDays(2);

    @Override
    protected void manualSyncCustomStaticFields() {
        REFRESH_TOKEN_KEY_FORMAT = ProjectProperties.NAME + ":" +
                AuthProperties.NAME + ":" +
                refreshTokenPrefix + ":" +
                "%s";
        LOGIN_RECORD_KEY_FORMAT = ProjectProperties.NAME + ":" +
                AuthProperties.NAME + ":" +
                loginRecordPrefix + ":" +
                "%s";
        LOGIN_LOCKED_KEY_FORMAT = ProjectProperties.NAME + ":" +
                AuthProperties.NAME + ":" +
                loginLockedPrefix + ":" +
                "%s";
    }

}
