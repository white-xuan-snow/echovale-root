package com.echovale.shared.application.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/15 15:37
 */

@Component
@Aspect
@Slf4j
public class MethodsInvokingTime {


    @Pointcut("execution(* com.echovale.*.domain.service.*.*(..))")
//    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void supplyServiceMethodsPointCut() {}


    @Around("supplyServiceMethodsPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        log.info("【性能监控】 方法：{} 调用耗时：{}ms", joinPoint.getSignature().getName(), (end - start));
        return result;
    }



}
