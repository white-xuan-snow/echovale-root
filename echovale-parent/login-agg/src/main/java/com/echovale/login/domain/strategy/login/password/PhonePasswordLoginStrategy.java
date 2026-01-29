package com.echovale.login.domain.strategy.login.password;

import com.echovale.login.domain.aggregate.User;
import com.echovale.login.domain.entity.LoginType;
import com.echovale.login.infrastructure.query.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 02:20
 */

@Component
@RequiredArgsConstructor
public class PhonePasswordLoginStrategy extends AbstractPasswordLoginStrategy {

    private final UserQueryService userQueryService;

    @Override
    protected User findUser(String identifier) {
        return userQueryService.queryUserByPhone(identifier);
    }

    @Override
    public LoginType getLoginType() {
        return LoginType.PASSWORD_PHONE;
    }
}
