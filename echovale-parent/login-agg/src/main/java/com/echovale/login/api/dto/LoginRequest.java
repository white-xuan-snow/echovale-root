package com.echovale.login.api.dto;

import com.alibaba.fastjson.annotation.JSONType;
import com.echovale.login.domain.entity.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/16 17:53
 */


@Data
@Builder
@AllArgsConstructor
public class LoginRequest {
    private String clientId;
    private String deviceId;
    private String ipAddress;
    private String identifier;
    private String credential;
    private LoginType loginType;
}
