package com.echovale.login.domain.service.impl;

import com.echovale.login.domain.service.LoginSecurityService;
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

    @Override
    public void recordFailure(String id, String ipAddress) {
        loginRecordRedisStore.increment(id);
        loginRecordRedisStore.increment(ipAddress);
    }

    @Override
    public boolean checkPreConditions(String identifier) {
        return loginRecordRedisStore.hasKey(identifier);
    }
}
