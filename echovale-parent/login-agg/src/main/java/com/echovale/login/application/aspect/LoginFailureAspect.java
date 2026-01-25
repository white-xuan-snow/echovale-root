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
        if (args.length == 0 || !(args[0] instanceof LoginCommand command)) {
            log.warn("无法发布登录失败事件：参数不匹配");
            return;
        }

        LoginFailedEvent event = LoginFailedEvent.builder()
                .id(command.getIdentifier())
                .reason(ex.getMessage())
                .source(this)
                .ipAddress(command.getIpAddress())
                .build();




        log.info("检测到登录失败，发布事件: id={}, reason={}", event.getId(), ex.getMessage());

        eventPublisher.publishEvent(event);
    }

}
