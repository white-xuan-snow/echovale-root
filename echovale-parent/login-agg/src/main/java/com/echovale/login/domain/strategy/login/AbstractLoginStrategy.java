package com.echovale.login.domain.strategy.login;

import com.echovale.login.api.vo.LoginResult;
import com.echovale.login.application.command.LoginCommand;
import com.echovale.login.domain.aggregate.User;
import com.echovale.login.domain.exception.BadConditionsException;
import com.echovale.login.domain.exception.BaseLoginException;
import com.echovale.login.domain.service.LoginSecurityService;
import com.echovale.login.infrastructure.properties.JwtAuthProperties;
import com.echovale.login.infrastructure.properties.LoginStrategyProperties;
import com.echovale.login.infrastructure.security.jwt.JwtAuthTokenService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/18 13:46
 */

@Component
public abstract class AbstractLoginStrategy implements LoginStrategy {

    @Autowired
    private JwtAuthTokenService jwtAuthTokenService;
    @Autowired
    private LoginSecurityService loginSecurityService;

    @Override
    public LoginResult login(LoginCommand command, HttpServletResponse response) {
        preValidate(command);

        User user = authenticate(command);

        String accessToken = jwtAuthTokenService.generateAccessToken(user);
        String refreshToken = jwtAuthTokenService.generateRefreshToken(user, command);

        LoginResult res = LoginResult.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(user)
                .build();

        postProcess(command, res, response);

        return res;
    }



    private User authenticate(LoginCommand command) {
        User user = findUser(command.getIdentifier());

        String credential = user == null ? LoginStrategyProperties.DUMMY_CREDENTIAL : command.getCredential();
        user = user == null ? User.onlySetPassword(LoginStrategyProperties.DUMMY_CREDENTIAL + "a") : user;
        boolean match = matcher(user, credential);

        if (user == null || !match) {
            throw buildLoginException(command);
        }

        return user;
    }

    protected abstract BaseLoginException buildLoginException(LoginCommand command);

    protected abstract boolean matcher(User user, String credential);

    protected abstract User findUser(String identifier);

    private void postProcess(LoginCommand command, LoginResult res, HttpServletResponse response) {
        String refreshToken = res.getRefreshToken();

        setRefreshTokenCookie(refreshToken, response);

    }

    private void setRefreshTokenCookie(String refreshToken, HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(JwtAuthProperties.REFRESH_EXPIRATION)
                .sameSite("Lax")
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private void preValidate(LoginCommand command) {
        // TODO 预校验
        boolean hasConditions = loginSecurityService.checkIdConditions(command.getIdentifier());

        if (hasConditions) {
            throw new BadConditionsException(command.getIdentifier());
        }
    }
}
