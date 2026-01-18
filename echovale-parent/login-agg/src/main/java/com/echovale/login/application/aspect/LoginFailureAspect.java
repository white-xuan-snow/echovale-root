package com.echovale.login.application.aspect;

import com.echovale.login.application.command.LoginCommand;
import com.echovale.login.domain.event.LoginFailedEvent;
import com.echovale.login.domain.exception.BaseLoginException;
import com.echovale.login.infrastructure.converter.LoginConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/18 17:04
 */


@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LoginFailureAspect {

    private final ApplicationEventPublisher eventPublisher;
    private final LoginConverter loginConverter;


    @AfterThrowing(
            pointcut = "execution(* com.echovale.login.domain.strategy.LoginStrategy.login(..))",
            throwing = "ex"
    )
    public void handleLoginFailure(JoinPoint joinPoint, BaseLoginException ex) {
        Object[] args = joinPoint.getArgs();
        if (args.length == 0 || !(args[0] instanceof LoginCommand)) {
            log.warn("无法发布登录失败事件：参数不匹配");
            return;
        }
        LoginCommand command = (LoginCommand) args[0];

        LoginFailedEvent event = loginConverter.byCommand(command, ex.getMessage());


        log.error("Login failed: {}", ex.getMessage());
    }

}
