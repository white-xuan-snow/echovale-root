package com.echovale.login.domain.service.impl;

import com.echovale.login.domain.aggregate.User;
import com.echovale.login.domain.entity.ClientType;
import com.echovale.login.domain.entity.LoginType;
import com.echovale.login.domain.service.TokenStoreService;
import com.echovale.login.domain.valueobject.UserId;
import com.echovale.login.infrastructure.redis.RefreshTokenRedisStore;
import com.echovale.login.infrastructure.security.jwt.JwtAuthTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/29 21:18
 */

@Service
@RequiredArgsConstructor
public class TokenStoreServiceImpl implements TokenStoreService {

    private final RefreshTokenRedisStore refreshTokenRedisStore;

    @Override
    public void recordRefresh(UserId userId, ClientType clientType, String deviceId, String jti) {
        refreshTokenRedisStore.set(jti, userId.getStringValue(), clientType, deviceId);
    }

    @Override
    public Boolean isRefreshValid(UserId userId, ClientType clientType, String deviceId, String jti) {
        return refreshTokenRedisStore.get(userId.getStringValue(), clientType, deviceId).equals(jti);
    }

    @Override
    public long getRemainingTtl(UserId id, ClientType clientType, String deviceId) {
        Long ttl = refreshTokenRedisStore.getRemainingTtl(id.getStringValue(), clientType, deviceId);
        return (ttl == null) ? 0 : ttl;
    }


}
