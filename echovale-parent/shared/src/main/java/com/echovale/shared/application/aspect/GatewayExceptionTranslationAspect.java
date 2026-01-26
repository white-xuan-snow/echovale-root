package com.echovale.shared.application.aspect;

import com.echovale.shared.domain.exception.BadGatewayInvokingException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/1 11:30
 */

@Aspect
@Component
public class GatewayExceptionTranslationAspect {

    @Pointcut("execution(* com.echovale.shared.external..*.*(..))")
    public void gatewayMethods() {
    }

    @Around("gatewayMethods()")
    public Object handleGatewayExceptions(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            throw new BadGatewayInvokingException(e.getMessage());
        }
    }
}