package com.echovale.shared.annotation;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 30531
 */
@Service
public @interface DomainService {
    String value() default "";
}
