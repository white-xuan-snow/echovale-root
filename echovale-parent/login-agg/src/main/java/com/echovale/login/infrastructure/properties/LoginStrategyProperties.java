package com.echovale.login.infrastructure.properties;

import com.echovale.shared.utils.StringUtil;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/18 15:46
 */


@Component
@ConfigurationProperties(prefix = "login.strategy")
@Data
public class LoginStrategyProperties {
    /**
     * @description: 假凭据，防止在登录策略中用户不存在时，过快的抛出异常（密码校验会更加耗时）
     */
    private String dummyCredential;
    /**
     * @description: 密码校验失败时，返回的提示信息
     */
    private String passwordUnauthorizedMessage;
    /**
     * @description: 验证码校验失败时，返回的提示信息
     */
    private String captchaUnauthorizedMessage;
    /**
     * @description: 预条件校验失败时，返回的提示信息
     */
    private String badConditionsMessage;

    public static String DUMMY_CREDENTIAL;
    public static String PASSWORD_UNAUTHORIZED_MESSAGE;
    public static String CAPTCHA_UNAUTHORIZED_MESSAGE;
    public static String BAD_CONDITIONS_MESSAGE;


    @PostConstruct
    private void init() {
        DUMMY_CREDENTIAL = this.dummyCredential;
        PASSWORD_UNAUTHORIZED_MESSAGE = StringUtil.oneOf(this.passwordUnauthorizedMessage, "用户名或密码错误");
        CAPTCHA_UNAUTHORIZED_MESSAGE = StringUtil.oneOf(this.captchaUnauthorizedMessage, "用户名或验证码错误");
        BAD_CONDITIONS_MESSAGE = StringUtil.oneOf(this.badConditionsMessage, "登录次数过多，请稍后重新尝试");
    }
}
