package com.echovale.login.domain.strategy.login;


import com.echovale.login.api.vo.LoginResult;
import com.echovale.login.application.command.LoginCommand;
import com.echovale.login.domain.entity.LoginType;
import jakarta.servlet.http.HttpServletResponse;


/**
 * @author 30531
 */
public interface LoginStrategy {

    LoginType getLoginType();

    LoginResult login(LoginCommand command);
}
