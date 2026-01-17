package com.echovale.login.infrastructure.captcha.config;

import cloud.tianai.captcha.application.ImageCaptchaApplication;
import cloud.tianai.captcha.spring.plugins.secondary.SecondaryVerificationApplication;
import com.echovale.common.domain.api.exception.UnauthorizedException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 01:22
 */


@Component
@RequiredArgsConstructor
public class LoginSecondaryCaptchaInterceptor implements HandlerInterceptor {

    private final ImageCaptchaApplication imageCaptchaApplication;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取二次验证token
        String secondaryToken = request.getHeader("Secondary_Token");

        if (StringUtils.isBlank(secondaryToken)) {
            throw new UnauthorizedException("二次验证Token为空，请先完成验证码校验");
        }

        // 调用tianai-captcha进行二次验证
        boolean isValid = ((SecondaryVerificationApplication) imageCaptchaApplication).secondaryVerification(secondaryToken);

        if (isValid) {
            return true;
        } else {
            throw new UnauthorizedException("二次验证失败，请重新验证");
        }
    }

}
