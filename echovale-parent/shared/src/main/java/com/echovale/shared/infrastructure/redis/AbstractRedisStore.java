package com.echovale.shared.infrastructure.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 22:07
 */


@Component
public abstract class AbstractRedisStore<V> implements RedisStore<V> {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    protected abstract String getKeyFormat();

    protected abstract Duration getExpire();
    protected String buildKey(Object... args) {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("错误参数");
        }
        return String.format(getKeyFormat(), args);
    }

    @Override
    public void set(V value, Object... args) {
        String key = buildKey(args);
        String valKey = value.toString();
        if (getExpire() != null) {
            stringRedisTemplate.opsForValue().set(key, valKey, getExpire());
        } else {
            stringRedisTemplate.opsForValue().set(key, valKey);
        }
    }

    @Override
    public void increment(Object... args) {
        String key = buildKey(args);
        stringRedisTemplate.opsForValue().increment(key);
        stringRedisTemplate.expire(key, getExpire());
    }

    @Override
    public V get(Object... args) {
        String val = stringRedisTemplate.opsForValue().get(buildKey(args));
        return toV(val);
    }

    protected abstract V toV(String val);

    @Override
    public void delete(Object... args) {
        stringRedisTemplate.delete(buildKey(args));
    }

    @Override
    public boolean hasKey(Object... args) {
        return stringRedisTemplate.hasKey(buildKey(args));
    }


}
