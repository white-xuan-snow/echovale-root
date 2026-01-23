package com.echovale.login.domain.service;


import org.springframework.stereotype.Service;

@Service
public interface LoginSecurityService {
    void recordFailure(String id, String ipAddress);

    boolean checkIdConditions(String identifier);

    boolean checkIpConditions(String ipAddress);
}
