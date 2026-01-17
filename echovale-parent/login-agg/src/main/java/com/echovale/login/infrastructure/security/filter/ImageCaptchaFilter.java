package com.echovale.login.infrastructure.security.filter;

import cloud.tianai.captcha.application.ImageCaptchaApplication;
import cloud.tianai.captcha.spring.plugins.secondary.SecondaryVerificationApplication;
import com.echovale.common.domain.api.exception.UnauthorizedException;
import com.echovale.login.infrastructure.constant.LoginPaths;
import com.echovale.login.infrastructure.properties.ImageCaptchaProperties;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 16:29
 */

@Component
@RequiredArgsConstructor
public class ImageCaptchaFilter extends OncePerRequestFilter {

    private final ImageCaptchaApplication imageCaptchaApplication;

    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver resolver;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (LoginPaths.LOGIN.equals(request.getRequestURI())) {
                String secondaryToken = request.getHeader(ImageCaptchaProperties.SECONDARY_TOKEN_HEADER_NAME);
                validate(secondaryToken);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            resolver.resolveException(request, response, null, new UnauthorizedException("二次验证失败"));
        }
    }



    private void validate(String secondaryToken) {
        if (StringUtils.isBlank(secondaryToken)) {
            throw new UnauthorizedException("二次验证Token为空，请先完成验证码校验");
        }
        // debug模式，跳过二次验证
        if (secondaryToken.equals(ImageCaptchaProperties.DEBUG_SECONDARY_TOKEN)) {
            return;
        }
        boolean isValid = ((SecondaryVerificationApplication) imageCaptchaApplication).secondaryVerification(secondaryToken);
        if (!isValid) {
            throw new UnauthorizedException("二次验证失败");
        }
    }
}
