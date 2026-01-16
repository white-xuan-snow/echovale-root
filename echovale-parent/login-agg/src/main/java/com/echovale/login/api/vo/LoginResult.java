package com.echovale.login.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 01:08
 */

@Value
@Builder
@AllArgsConstructor
public class LoginResult {
    String token;
    UserVO user;
    Long loginTime;
}
