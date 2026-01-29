package com.echovale.login.application.service;


import com.echovale.login.api.vo.LoginResult;
import com.echovale.login.application.command.LoginCommand;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public interface LoginApplicationService {
    LoginResult checkAndLogin(LoginCommand command, HttpServletResponse response);
}
