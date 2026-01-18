package com.echovale.login.domain.strategy;


import com.echovale.login.api.dto.LoginRequest;
import com.echovale.login.api.vo.LoginResult;
import com.echovale.login.application.command.LoginCommand;
import com.echovale.login.domain.entity.LoginType;


/**
 * @author 30531
 */
public interface LoginStrategy {

    LoginType getLoginType();

    LoginResult login(LoginCommand command);
}
