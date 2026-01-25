package com.echovale.login.domain.service.impl;

import com.echovale.login.domain.service.LoginSecurityService;
import com.echovale.login.infrastructure.properties.LoginRedisProperties;
import com.echovale.login.infrastructure.redis.LoginLockedRedisStore;
import com.echovale.login.infrastructure.redis.LoginRecordRedisStore;
import com.netease.music.api.autoconfigure.configuration.constant.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/22 23:21
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginSecurityServiceImpl implements LoginSecurityService {

    private final LoginRecordRedisStore loginRecordRedisStore;
    private final LoginLockedRedisStore loginLockedRedisStore;

    @Override
    public void recordFailure(String id, String ipAddress) {
        loginRecordRedisStore.increment(id);
        loginRecordRedisStore.increment(ipAddress);

        if (loginRecordRedisStore.get(id) > LoginRedisProperties.LOGIN_ID_RECORD_MAX_TIMES) {
            loginLockedRedisStore.set(true, id);
        }
        if (loginRecordRedisStore.get(ipAddress) > LoginRedisProperties.LOGIN_IP_RECORD_MAX_TIMES) {
            loginLockedRedisStore.set(true, ipAddress);
        }
    }

    @Override
    public boolean checkIdConditions(String identifier) {
        return loginLockedRedisStore.hasKey(identifier);
    }

    @Override
    public boolean checkIpConditions(String ipAddress) {
        return loginLockedRedisStore.hasKey(ipAddress);
    }
}
