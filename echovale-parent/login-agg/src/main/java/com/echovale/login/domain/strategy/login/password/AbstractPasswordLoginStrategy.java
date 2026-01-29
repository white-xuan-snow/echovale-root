package com.echovale.login.domain.strategy.login.password;

import com.echovale.login.application.command.LoginCommand;
import com.echovale.login.domain.aggregate.User;
import com.echovale.login.domain.exception.BadCredentialsException;
import com.echovale.login.domain.exception.BaseLoginException;
import com.echovale.login.domain.strategy.login.AbstractLoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 01:12
 */

@Component
public abstract class AbstractPasswordLoginStrategy extends AbstractLoginStrategy {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    protected boolean matcher(User user, String credential) {
        return passwordEncoder.matches(credential, user.getPassword());
    }


    @Override
    protected BaseLoginException buildLoginException(LoginCommand command) {
        return new BadCredentialsException(command.getIdentifier());
    }

}
