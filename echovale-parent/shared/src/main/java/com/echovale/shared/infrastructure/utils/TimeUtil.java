package com.echovale.shared.infrastructure.utils;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/15 22:50
 */

@Component
public class TimeUtil {


    public static Date millis(long increment) {
        return new Date(System.currentTimeMillis() + increment);
    }
    public static Date millis() {
        return new Date(System.currentTimeMillis());
    }

}
