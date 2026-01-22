package com.echovale.login.infrastructure.redis;

import com.echovale.login.infrastructure.properties.LoginRedisProperties;
import com.echovale.shared.redis.AbstractRedisStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/22 23:39
 */

@Component
@RequiredArgsConstructor
public class LoginRecordRedisStore extends AbstractRedisStore<String> {
    @Override
    protected String getKeyFormat() {
        return LoginRedisProperties.LOGIN_RECORD_KEY_FORMAT;
    }

    @Override
    protected Duration getExpire() {
        return LoginRedisProperties.LOGIN_RECORD_EXPIRE;
    }
}
