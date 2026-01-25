package com.echovale.login.infrastructure.redis;

import com.echovale.login.infrastructure.properties.LoginRedisProperties;
import com.echovale.shared.redis.AbstractRedisStore;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/23 16:48
 */

@Component
public class LoginLockedRedisStore extends AbstractRedisStore<Boolean>  {
    @Override
    protected String getKeyFormat() {
        return LoginRedisProperties.LOGIN_LOCKED_KEY_FORMAT;
    }

    @Override
    protected Duration getExpire() {
        return LoginRedisProperties.LOGIN_LOCKED_EXPIRE;
    }

    @Override
    protected Boolean toV(String val) {
        return Boolean.parseBoolean(val);
    }
}
