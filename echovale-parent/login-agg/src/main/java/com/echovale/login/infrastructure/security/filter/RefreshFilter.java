package com.echovale.login.infrastructure.security.filter;

import com.echovale.login.domain.aggregate.User;
import com.echovale.login.domain.service.TokenStoreService;
import com.echovale.login.domain.valueobject.UserId;
import com.echovale.login.infrastructure.constant.LoginPaths;
import com.echovale.login.infrastructure.security.jwt.JwtAuthTokenService;
import com.echovale.shared.domain.exception.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/27 15:58
 */
// 弃用
// @Component才生效
@RequiredArgsConstructor
public class RefreshFilter extends OncePerRequestFilter {

    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver resolver;
    private final JwtAuthTokenService jwtAuthTokenService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (LoginPaths.REFRESH.equals(request.getRequestURI())) {

                String refreshToken = request.getHeader("Refresh-Token");

                User user = new User();

                if (refreshToken != null && jwtAuthTokenService.validateRefreshToken(refreshToken, user)) {
                    UserId userId = user.getId();
                    if (userId.isNotNull() && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
            filterChain.doFilter(request, response);
        } catch (UnauthorizedException e) {
            resolver.resolveException(request, response, null, new UnauthorizedException("Jwt验证失败"));
        }
    }
}
