package com.echovale.login.domain.strategy.impl;

import com.echovale.login.api.vo.LoginResult;
import com.echovale.login.application.command.LoginCommand;
import com.echovale.login.domain.aggregate.User;
import com.echovale.login.domain.exception.BadConditionsException;
import com.echovale.login.domain.exception.BaseLoginException;
import com.echovale.login.domain.service.LoginSecurityService;
import com.echovale.login.domain.strategy.LoginStrategy;
import com.echovale.login.infrastructure.properties.LoginStrategyProperties;
import com.echovale.login.infrastructure.security.jwt.JwtAuthTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/18 13:46
 */

@Component
public abstract class AbstractLoginStrategy implements LoginStrategy {

    @Autowired
    private JwtAuthTokenUtil jwtAuthTokenUtil;
    @Autowired
    private LoginSecurityService loginSecurityService;

    @Override
    public LoginResult login(LoginCommand command) {
        preValidate(command);

        LoginResult result = authenticate(command);

        postProcess(command, result);

        return result;
    }

    private LoginResult authenticate(LoginCommand command) {
        User user = findUser(command.getIdentifier());

        String credential = user == null ? LoginStrategyProperties.DUMMY_CREDENTIAL : command.getCredential();
        user = user == null ? User.builder().password(LoginStrategyProperties.DUMMY_CREDENTIAL + "a").build() : user;
        boolean match = matcher(user, credential);
        if (user == null || !match) {
            throw buildLoginException(command);
        }

        // TODO LoginResult转换逻辑
        return LoginResult.builder()
                .accessToken(jwtAuthTokenUtil.generateAccessToken(user))
                .build();
    }

    protected abstract BaseLoginException buildLoginException(LoginCommand command);

    protected abstract String getUnauthorizedMsg();

    protected abstract boolean matcher(User user, String credential);

    protected abstract User findUser(String identifier);

    private void postProcess(LoginCommand command, LoginResult result) {
        // TODO 登录成功后处理
    }

    private void preValidate(LoginCommand command) {
        // TODO 预校验
        boolean hasConditions = loginSecurityService.checkIdConditions(command.getIdentifier());

        if (hasConditions) {
            throw new BadConditionsException(command.getIdentifier());
        }
    }
}
