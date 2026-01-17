package com.echovale.login.api.vo;

import com.echovale.login.domain.valueobject.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 01:09
 */

@Value
@Builder
@AllArgsConstructor
public class UserInfo {
    UserId id;
    String username;
    String avatar;
    List<String> roles;
}
