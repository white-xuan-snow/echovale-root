package com.echovale.login.infrastructure.security.filter;

import com.echovale.login.infrastructure.constant.Auth;
import com.echovale.shared.domain.exception.UnauthorizedException;
import com.echovale.login.domain.aggregate.User;
import com.echovale.login.domain.valueobject.UserId;
import com.echovale.login.infrastructure.security.jwt.JwtAuthTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/15 22:38
 */

@Component
@RequiredArgsConstructor
public class AccessTokenFilter extends OncePerRequestFilter {

    private final JwtAuthTokenService jwtAuthTokenService;
    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            if (!Auth.LOGIN.equals(request.getRequestURI())) {
                String token = getTokenFromRequest(request);

                User user = new User();

                if (token != null && jwtAuthTokenService.validateAccessToken(token, user)) {
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

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
