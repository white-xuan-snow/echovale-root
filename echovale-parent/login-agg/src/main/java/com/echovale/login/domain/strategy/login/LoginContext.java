package com.echovale.login.domain.strategy.login;

import com.echovale.shared.domain.exception.NotImplementedException;
import com.echovale.login.api.vo.LoginResult;
import com.echovale.login.application.command.LoginCommand;
import com.echovale.login.domain.entity.LoginType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 02:30
 */

@Component
@RequiredArgsConstructor
public class LoginContext {

    private final List<LoginStrategy> loginStrategies;

    private Map<LoginType, LoginStrategy> loginStrategyMap;


    @PostConstruct
    public void init() {
        loginStrategyMap = loginStrategies.stream()
                .collect(Collectors.toMap(LoginStrategy::getLoginType, s -> s));
    }


    public LoginResult execute(LoginCommand command)  {

        LoginStrategy strategy = loginStrategyMap.get(command.getLoginType());

        if (strategy == null) {
            // TODO 待验证
            throw new NotImplementedException("不支持的登录类型: " + command.getLoginType());
        }

        return strategy.login(command);
    }
}
