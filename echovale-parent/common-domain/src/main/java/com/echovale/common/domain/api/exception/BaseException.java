package com.echovale.common.domain.api.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException {
    private final Integer code;
    private final String message;
    public BaseException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.message = msg;
    }
    public BaseException(Integer code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.message = msg;
    }
}
