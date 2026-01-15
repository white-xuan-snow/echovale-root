package com.echovale.login.application.command;

import com.echovale.login.domain.aggregate.User;
import com.echovale.login.domain.valueobject.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/15 23:39
 */


@Value
@Builder
@AllArgsConstructor
public class ObtainUserRolesCommand {
    UserId userId;

    public static ObtainUserRolesCommand of(User user) {
        return ObtainUserRolesCommand.builder()
                .userId(user.getId())
                .build();
    }
}
