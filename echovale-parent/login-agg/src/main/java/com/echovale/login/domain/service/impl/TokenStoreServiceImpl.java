package com.echovale.login.domain.service.impl;

import com.echovale.login.domain.aggregate.User;
import com.echovale.login.domain.service.TokenStoreService;
import com.echovale.login.domain.valueobject.UserId;
import com.echovale.login.infrastructure.redis.RefreshTokenRedisStore;
import com.echovale.login.infrastructure.security.jwt.JwtAuthTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public void recordRefresh(UserId userId, String clientId, String deviceId, String jti) {
        refreshTokenRedisStore.set(jti, userId.getStringValue(), clientId, deviceId);
    }

    @Override
    public Boolean isRefreshValid(UserId userId, String jti) {
        return refreshTokenRedisStore.get(userId.getStringValue()).equals(jti);
    }

    @Override
    public long getRemainingTtl(UserId id) {
        Long ttl = refreshTokenRedisStore.getRemainingTtl(id.getStringValue());
        return (ttl == null) ? 0 : ttl;
    }


}
