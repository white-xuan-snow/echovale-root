package com.echovale.login.domain.strategy.impl;

import com.echovale.login.domain.aggregate.User;
import com.echovale.login.domain.entity.LoginType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 02:24
 */

@Component
@RequiredArgsConstructor
public class EmailCaptchaLoginStrategy extends AbstractCaptchaLoginStrategy {
    @Override
    protected User findUser(String identifier) {
        return null;
    }

    @Override
    public LoginType getLoginType() {
        return null;
    }
}
