package com.echovale.login.api.vo;

import com.echovale.login.domain.aggregate.User;
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
    User user;
    String accessToken;
    String refreshToken;
}
