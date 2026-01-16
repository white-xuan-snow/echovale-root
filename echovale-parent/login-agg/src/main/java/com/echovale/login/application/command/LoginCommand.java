package com.echovale.login.application.command;

import com.echovale.login.domain.entity.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 01:37
 */

@Data
@Builder
@AllArgsConstructor
public class LoginCommand {
    private String clientId;
    private String deviceId;
    private String ipAddress;
    private String identifier;
    private String credential;
    private LoginType loginType;
}
