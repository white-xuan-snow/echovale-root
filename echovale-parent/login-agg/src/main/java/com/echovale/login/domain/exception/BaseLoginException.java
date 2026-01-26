package com.echovale.login.domain.exception;

import lombok.Getter;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/18 16:28
 */

@Getter
public abstract class BaseLoginException extends RuntimeException {
    private final String identifier;

    public BaseLoginException(String message, String identifier) {
        super(message);
        this.identifier = identifier;
    }

}
