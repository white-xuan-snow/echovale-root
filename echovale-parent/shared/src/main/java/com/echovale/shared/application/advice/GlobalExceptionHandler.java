package com.echovale.shared.application.advice;

import com.echovale.shared.domain.exception.BaseException;
import com.echovale.shared.infrastructure.presistence.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
                .status(determineHttpStatus(ex.getCode()))
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


    private HttpStatus determineHttpStatus(int errorCode) {
        // 逻辑：取出 6 位错误码的前 3 位，或者直接根据区间判断
        if (errorCode < 600) {
            return HttpStatus.valueOf(errorCode);
        }
        int prefix = errorCode / 1000;
        try {
            return HttpStatus.valueOf(prefix);
        } catch (Exception e) {
            return HttpStatus.OK;
        }
    }

}
