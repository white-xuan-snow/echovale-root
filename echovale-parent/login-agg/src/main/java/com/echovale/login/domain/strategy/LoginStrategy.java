package com.echovale.login.domain.strategy;


import com.echovale.login.api.dto.LoginRequest;
import com.echovale.login.api.vo.LoginResult;
import com.echovale.login.application.command.LoginCommand;
import com.echovale.login.domain.entity.LoginType;


public interface LoginStrategy {

    LoginType getLoginType();

    default LoginResult login(LoginCommand command) {
        // 前置校验
        preValidate(command);
        // 核心认证
        LoginResult result = authenticate(command);
        // 后置处理
        postProcess(command, result);
        return result;
    }

    LoginResult authenticate(LoginCommand command);

    void preValidate(LoginCommand command);

    void postProcess(LoginCommand command, LoginResult result);
}
