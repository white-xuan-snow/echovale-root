package com.echovale.login.application.service.impl;

import com.echovale.login.api.vo.LoginResult;
import com.echovale.login.application.command.LoginCommand;
import com.echovale.login.application.service.LoginApplicationService;
import com.echovale.login.domain.service.impl.LoginContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/27 02:31
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginApplicationServiceImpl implements LoginApplicationService {

    private final LoginContext loginContext;


    @Override
    public LoginResult checkAndLogin(LoginCommand command) {

        return loginContext.execute(command);
    }
}
