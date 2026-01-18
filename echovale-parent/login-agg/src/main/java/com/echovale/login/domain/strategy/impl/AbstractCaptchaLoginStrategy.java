package com.echovale.login.domain.strategy.impl;

import com.echovale.login.application.command.LoginCommand;
import com.echovale.login.domain.aggregate.User;
import com.echovale.login.domain.exception.BadCredentialsException;
import com.echovale.login.domain.exception.BaseLoginException;
import com.echovale.login.domain.exception.CaptchaException;
import com.echovale.login.infrastructure.properties.LoginStrategyProperties;
import com.echovale.shared.utils.StringUtil;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 02:10
 */

@Component
public abstract class AbstractCaptchaLoginStrategy extends AbstractLoginStrategy {


    @Override
    protected String getUnauthorizedMsg() {
        return LoginStrategyProperties.CAPTCHA_UNAUTHORIZED_MESSAGE;
    }

    @Override
    protected boolean matcher(User user, String credential) {
        // TODO 接入验证码平台
        return false;
    }


    @Override
    protected BaseLoginException buildLoginException(LoginCommand command) {
        return new CaptchaException(command.getIdentifier());
    }
}
