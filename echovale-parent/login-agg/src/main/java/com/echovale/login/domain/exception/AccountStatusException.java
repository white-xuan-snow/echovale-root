package com.echovale.login.domain.exception;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/18 16:36
 */


public class AccountStatusException extends BaseLoginException {
    public AccountStatusException(String message, String identifier) {
        super(message, identifier);
    }
}
