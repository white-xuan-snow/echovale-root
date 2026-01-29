package com.echovale.login.infrastructure.redis;

import com.echovale.login.infrastructure.properties.JwtAuthProperties;
import com.echovale.shared.infrastructure.redis.AbstractRedisStore;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/28 18:28
 */

@Component
public class JwtJtiRedis extends AbstractRedisStore<String> {
    @Override
    protected String getKeyFormat() {
        return JwtAuthProperties.JWT_JTI_KEY_FORMAT;
    }

    @Override
    protected Duration getExpire() {
        return JwtAuthProperties.JWT_JTI_EXPIRE;
    }

    @Override
    protected String toV(String val) {
        return val;
    }
}
