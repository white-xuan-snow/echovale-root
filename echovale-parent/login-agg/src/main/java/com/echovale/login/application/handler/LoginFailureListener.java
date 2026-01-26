package com.echovale.login.application.handler;

import com.echovale.login.domain.event.LoginFailedEvent;
import com.echovale.login.domain.service.LoginSecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/22 23:17
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginFailureListener {

    private final LoginSecurityService loginSecurityService;


    @Async
    @EventListener
    public void handle(LoginFailedEvent event) {
        log.info("开始处理登录失败事件：{}", event.getId());
        loginSecurityService.recordFailure(event.getId(), event.getIpAddress());
    }
}
