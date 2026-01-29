package com.echovale.login.domain.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/18 17:13
 */

@Getter
public class LoginFailedEvent {
    private final String id;
    private final String ipAddress;
    private final String reason;

    private final LocalDateTime occurredOn;

    //
    @Builder
    public LoginFailedEvent(String id, String ipAddress, String reason) {
        this.id = id;
        this.ipAddress = ipAddress;
        this.reason = reason;
        this.occurredOn = LocalDateTime.now();
    }
}