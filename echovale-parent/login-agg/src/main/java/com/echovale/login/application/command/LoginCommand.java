package com.echovale.login.application.command;

import com.echovale.login.domain.entity.ClientType;
import com.echovale.login.domain.entity.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 01:37
 */

@Value
@Builder
@AllArgsConstructor
public class LoginCommand {
    ClientType clientType;
    String deviceId;
    String ipAddress;
    String identifier;
    String credential;
    String oldRefreshToken;
    LoginType loginType;
    Boolean isValidate;
}
