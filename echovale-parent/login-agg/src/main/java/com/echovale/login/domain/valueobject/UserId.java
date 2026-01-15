package com.echovale.login.domain.valueobject;

import com.echovale.shared.domain.valueobject.Init;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/15 22:42
 */


@Value
@Builder
@AllArgsConstructor
public class UserId {
    Long id;

    public UserId() {
        this.id = Init.ZERO_L;
    }

    public static UserId of(Object obj) {
        if (obj instanceof Long) {
            return new UserId((Long) obj);
        } else {
            return new UserId();
        }
    }

    public boolean isNotNull() {
        return id != null && id != Init.ZERO_L;
    }
}
