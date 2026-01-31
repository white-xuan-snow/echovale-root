package com.echovale.login.api.dto;

import com.alibaba.fastjson.annotation.JSONType;
import com.echovale.login.domain.entity.ClientType;
import com.echovale.login.domain.entity.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.experimental.SuperBuilder;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/16 17:53
 */


@Value
@SuperBuilder
@AllArgsConstructor
public class LoginRequest extends BaseRequest {
    String identifier;
    String credential;
    LoginType loginType;
}
