package com.echovale.login.domain.strategy.login;

import com.echovale.login.api.vo.LoginResult;
import com.echovale.login.application.command.LoginCommand;
import com.echovale.login.domain.aggregate.User;
import com.echovale.login.domain.exception.BadConditionsException;
import com.echovale.login.domain.exception.BaseLoginException;
import com.echovale.login.domain.service.LoginSecurityService;
import com.echovale.login.infrastructure.properties.LoginStrategyProperties;
import com.echovale.login.infrastructure.security.jwt.JwtAuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public LoginResult login(LoginCommand command) {
        preValidate(command);

        User user = authenticate(command);

        String accessToken = jwtAuthTokenService.generateAccessToken(user);
        String refreshToken = jwtAuthTokenService.generateRefreshToken(user, command);

        LoginResult res = LoginResult.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(user)
                .build();

        postProcess(command, res);

        return res;
    }


    private User authenticate(LoginCommand command) {
        User user = findUser(command);

        // 旧刷新令牌验证成功直接结束调用，无需进行凭证匹配验证
        if (command.getOldRefreshToken() != null && (isFindUserHasValidation() || jwtAuthTokenService.validateRefreshToken(command, user))) {
            return user;
        }

        String credential = user == null ? LoginStrategyProperties.DUMMY_CREDENTIAL : command.getCredential();
        user = user == null ? User.onlySetPassword(LoginStrategyProperties.DUMMY_CREDENTIAL + "a") : user;
        boolean match = matcher(user, credential);

        if (user == null || !match) {
            throw buildLoginException(command);
        }

        return user;
    }

    protected boolean isFindUserHasValidation() {
        return false;
    }

    protected abstract BaseLoginException buildLoginException(LoginCommand command);

    protected abstract boolean matcher(User user, String credential);

    protected abstract User findUser(LoginCommand command);

    private void postProcess(LoginCommand command, LoginResult res) {

        // TODO 后处理
    }

    private void preValidate(LoginCommand command) {
        if (!LoginStrategyProperties.PRE_VALIDATE) {
            return;
        }
        // TODO 预校验
        boolean hasConditions = loginSecurityService.checkIdConditions(command.getIdentifier());

        if (hasConditions) {
            throw new BadConditionsException(command.getIdentifier());
        }
    }
}
