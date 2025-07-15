package com.echovale.api.advice;

import com.echovale.common.exception.BaseException;
import com.echovale.service.dto.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/7/15 19:26
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<Result> handleBaseException(final BaseException ex, final HttpServletRequest request) {
        return ResponseEntity
                .status(ex.getCode())
                .body(Result.fail(ex.getCode(), ex.getMessage()));
    }


}
