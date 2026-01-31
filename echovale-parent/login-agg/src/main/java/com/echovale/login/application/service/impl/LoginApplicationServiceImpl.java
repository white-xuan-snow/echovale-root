package com.echovale.login.application.service.impl;

import com.echovale.login.api.vo.LoginResult;
import com.echovale.login.application.command.LoginCommand;
import com.echovale.login.application.service.LoginApplicationService;
import com.echovale.login.domain.service.impl.LoginContext;
import com.echovale.login.infrastructure.properties.JwtAuthProperties;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/27 02:31
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginApplicationServiceImpl implements LoginApplicationService {

    private final LoginContext loginContext;


    @Override
    public LoginResult checkAndLogin(LoginCommand command, HttpServletResponse response) {
        LoginResult res = loginContext.execute(command);
        setRefreshTokenCookie(res.refreshToken(), response);
        return res;
    }



    private void setRefreshTokenCookie(String refreshToken, HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("Refresh-Token", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(JwtAuthProperties.REFRESH_EXPIRATION)
                .sameSite("Lax")
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
