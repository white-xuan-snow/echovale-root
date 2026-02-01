package com.echovale.login.domain.exception;

import com.echovale.login.domain.entity.AuthErrorCode;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/18 16:36
 */


public class AccountStatusException extends BaseLoginException {
    public AccountStatusException(String identifier) {
        super(AuthErrorCode.ACCOUNT_STATUS_ERROR, identifier);
    }
}
