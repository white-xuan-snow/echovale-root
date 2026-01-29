package com.echovale.login.domain.strategy.login.refresh;

import com.echovale.login.application.command.LoginCommand;
import com.echovale.login.domain.aggregate.User;
import com.echovale.login.domain.entity.LoginType;
import com.echovale.login.domain.exception.BaseLoginException;
import com.echovale.login.domain.exception.BadRefreshTokenException;
import com.echovale.login.domain.strategy.login.AbstractLoginStrategy;
import com.echovale.login.infrastructure.query.UserQueryService;
import com.echovale.login.infrastructure.security.jwt.JwtAuthTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/29 21:52
 */

@Component
@RequiredArgsConstructor
public class RefreshLoginStrategy extends AbstractLoginStrategy {

    private final JwtAuthTokenService jwtAuthTokenService;
    private final UserQueryService userQueryService;

    @Override
    protected BaseLoginException buildLoginException(LoginCommand command) {
        return new BadRefreshTokenException(command.getIdentifier());
    }

    @Override
    protected boolean matcher(User user, String credential) {
        return user.getId().isNotNull();
    }

    @Override
    protected User findUser(String identifier) {
        User user = new User();
        boolean isValid = jwtAuthTokenService.validateRefreshToken(identifier, user);
        if (!isValid) {
            return null;
        }
        return userQueryService.queryUserById(user.getId());
    }

    @Override
    public LoginType getLoginType() {
        return LoginType.REFRESH;
    }
}
