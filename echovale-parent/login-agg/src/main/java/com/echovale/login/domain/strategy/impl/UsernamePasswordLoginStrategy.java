package com.echovale.login.domain.strategy.impl;

import com.echovale.login.domain.aggregate.User;
import com.echovale.login.domain.entity.LoginType;
import com.echovale.login.infrastructure.query.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 02:14
 */

@Component
@RequiredArgsConstructor
public class UsernamePasswordLoginStrategy extends AbstractPasswordLoginStrategy {

    private final UserQueryService userQueryService;


    @Override
    protected User findUser(String identifier) {
        return userQueryService.queryUserByUsername(identifier);
    }

    @Override
    public LoginType getLoginType() {
        return LoginType.PASSWORD_USERNAME;
    }
}
