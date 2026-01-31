package com.echovale.login.domain.strategy.login.password;

import com.echovale.login.application.command.LoginCommand;
import com.echovale.login.domain.aggregate.User;
import com.echovale.login.domain.entity.LoginType;
import com.echovale.login.infrastructure.query.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 02:22
 */

@Component
@RequiredArgsConstructor
public class EmailPasswordLoginStrategy extends AbstractPasswordLoginStrategy {

    private final UserQueryService userQueryService;

    @Override
    protected User findUser(LoginCommand command) {
        return userQueryService.queryUserByEmail(command.getIdentifier());
    }

    @Override
    public LoginType getLoginType() {
        return LoginType.PASSWORD_EMAIL;
    }
}
