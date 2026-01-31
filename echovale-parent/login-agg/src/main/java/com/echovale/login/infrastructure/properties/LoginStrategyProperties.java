package com.echovale.login.infrastructure.properties;

import com.echovale.shared.infrastructure.properties.AbstractInitProperties;
import com.echovale.shared.infrastructure.utils.StringUtil;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
public class LoginStrategyProperties extends AbstractInitProperties {
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

    private String tokenUnauthorizedMessage;
    private Boolean preValidate;

    public static String DUMMY_CREDENTIAL;
    public static String PASSWORD_UNAUTHORIZED_MESSAGE = "用户名或密码错误";
    public static String CAPTCHA_UNAUTHORIZED_MESSAGE = "用户名或验证码错误";
    public static String BAD_CONDITIONS_MESSAGE = "登录次数过多，请稍后重新尝试";
    public static String TOKEN_UNAUTHORIZED_MESSAGE = "登录凭证错误，请重新登录";
    public static Boolean PRE_VALIDATE;

}
