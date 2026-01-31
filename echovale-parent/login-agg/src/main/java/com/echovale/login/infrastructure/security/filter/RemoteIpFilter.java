package com.echovale.login.infrastructure.security.filter;

import com.echovale.login.infrastructure.constant.Auth;
import com.echovale.login.infrastructure.properties.SecurityFilterProperties;
import com.echovale.shared.domain.exception.UnauthorizedException;
import com.echovale.login.domain.service.LoginSecurityService;
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
 * @date 2026/1/23 22:02
 */
@Component
@RequiredArgsConstructor
public class RemoteIpFilter extends OncePerRequestFilter {

    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final LoginSecurityService loginSecurityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (SecurityFilterProperties.REMOTE_IP_FILTER && Auth.LOGIN.equals(request.getRequestURI())) {
                String ip = request.getRemoteAddr();

                if (loginSecurityService.checkIpConditions(ip)) {
                    throw new UnauthorizedException("当前ip请求次数过多，请稍后尝试");
                }
            }
            filterChain.doFilter(request, response);
        } catch (UnauthorizedException e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}
