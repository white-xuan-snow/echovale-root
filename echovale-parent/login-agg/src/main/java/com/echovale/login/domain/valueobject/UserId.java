package com.echovale.login.domain.valueobject;

import com.alibaba.fastjson.annotation.JSONType;
import com.echovale.shared.domain.valueobject.Init;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/15 22:42
 */

@Slf4j
@Value
@Builder
@AllArgsConstructor
public class UserId {
    @JsonValue
    Long id;

    public UserId() {
        this.id = Init.ZERO_L;
    }

    public static UserId of(Object obj) {
        if (obj instanceof Long userIdLong) {
            return new UserId(userIdLong);
        } else if (obj instanceof String userIdString) {
            return new UserId(Long.valueOf(userIdString));
        } else {
            log.info("初始化失败，不支持的类型：{}", obj.getClass());
            return new UserId();
        }
    }

    public boolean isNotNull() {
        return id != null && id != Init.ZERO_L;
    }

    public String getStringValue() {
        return id.toString();
    }
    public Long getLongValue() {
        return id;
    }
}
