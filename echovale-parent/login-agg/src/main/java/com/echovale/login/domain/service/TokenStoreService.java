package com.echovale.login.domain.service;


import com.echovale.login.domain.valueobject.UserId;
import org.springframework.stereotype.Service;

@Service
public interface TokenStoreService {
    void recordRefresh(UserId userId, String clientId, String deviceId, String jti);

    Boolean isRefreshValid(UserId userid, String jti);

    long getRemainingTtl(UserId id);
}
