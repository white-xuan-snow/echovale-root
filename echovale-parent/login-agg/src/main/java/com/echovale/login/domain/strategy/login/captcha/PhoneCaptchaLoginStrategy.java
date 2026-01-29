package com.echovale.login.domain.strategy.login.captcha;

import com.echovale.login.domain.aggregate.User;
import com.echovale.login.domain.entity.LoginType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 02:23
 */


@Component
@RequiredArgsConstructor
public class PhoneCaptchaLoginStrategy extends AbstractCaptchaLoginStrategy {
    @Override
    protected User findUser(String identifier) {
        return null;
    }

    @Override
    public LoginType getLoginType() {
        return LoginType.CAPTCHA_PHONE;
    }
}
