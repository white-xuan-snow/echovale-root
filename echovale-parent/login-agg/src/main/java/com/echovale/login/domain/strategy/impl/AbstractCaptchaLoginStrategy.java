package com.echovale.login.domain.strategy.impl;

import com.echovale.common.domain.api.exception.UnauthorizedException;
import com.echovale.login.api.vo.LoginResult;
import com.echovale.login.application.command.LoginCommand;
import com.echovale.login.domain.aggregate.User;
import com.echovale.login.domain.strategy.LoginStrategy;
import com.echovale.login.infrastructure.converter.LoginConverter;
import com.echovale.login.infrastructure.security.jwt.JwtAuthTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 02:10
 */

@Component
public abstract class AbstractCaptchaLoginStrategy implements LoginStrategy {


    @Autowired
    private LoginConverter loginConverter;

    @Autowired
    private JwtAuthTokenUtil jwtAuthTokenUtil;

    @Override
    public LoginResult authenticate(LoginCommand command) {
        User user = findUser(command.getIdentifier());
        if (user == null) {
            throw new UnauthorizedException("用户名或验证码错误");
        }

        // TODO 对接验证码api
        boolean match = true;

        if (!match) {
            handleLoginFailure(user);
            throw new UnauthorizedException("用户名或验证码错误");
        }

        // TODO LoginResult转换逻辑
        return LoginResult.builder()
                .accessToken(jwtAuthTokenUtil.generateAccessToken(user))
                .build();
    }

    private void handleLoginFailure(User user) {
        // TODO Redis记录登录失败次数（使用Event推送任务）
    }

    protected abstract User findUser(String identifier);

    @Override
    public void preValidate(LoginCommand command) {

    }

    @Override
    public void postProcess(LoginCommand command, LoginResult result) {

    }
}
