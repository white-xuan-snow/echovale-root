package com.echovale.login.api.vo;

import com.echovale.login.domain.aggregate.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 01:08
 */

@Builder
public record LoginResult(User user, String accessToken, @JsonIgnore String refreshToken) {
}
