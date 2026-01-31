package com.echovale.login.domain.service;


import com.echovale.login.domain.entity.ClientType;
import com.echovale.login.domain.entity.LoginType;
import com.echovale.login.domain.valueobject.UserId;
import org.springframework.stereotype.Service;

@Service
public interface TokenStoreService {
    void recordRefresh(UserId userId, ClientType clientType, String deviceId, String jti);

    Boolean isRefreshValid(UserId userId, ClientType clientType, String deviceId , String jti);

    long getRemainingTtl(UserId id, ClientType clientType, String deviceId);
}
