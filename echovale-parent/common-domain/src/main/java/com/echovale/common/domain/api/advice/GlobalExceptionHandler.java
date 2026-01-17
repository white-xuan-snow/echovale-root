package com.echovale.common.domain.api.advice;

import com.echovale.common.domain.api.exception.BaseException;
import com.echovale.common.domain.infrastructure.presistence.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/7/15 19:26
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Result> handleBaseException(final BaseException ex, final HttpServletRequest request) {
        log.info("[handleBaseException]{}", ex.getMessage());
        return ResponseEntity
                .status(ex.getCode())
                .body(Result.fail(ex.getCode(), ex.getMessage()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result> handleFieldValidException(final MethodArgumentNotValidException ex, final HttpServletRequest request) {
        log.info("[handleFieldValidException]{}", ex.getMessage());
        BindingResult bindingResult = ex.getBindingResult();

        String errorMessage = "参数校验错误";

        if (bindingResult.hasGlobalErrors()) {
            errorMessage = bindingResult.getGlobalErrors().get(0).getDefaultMessage();
        } else if (bindingResult.hasFieldErrors()) {
            errorMessage = bindingResult.getFieldErrors().get(0).getDefaultMessage();
        }


        return ResponseEntity
                .status(ex.getStatusCode())
                .body(Result.fail(ex.getStatusCode().value(), errorMessage));
    }
}
