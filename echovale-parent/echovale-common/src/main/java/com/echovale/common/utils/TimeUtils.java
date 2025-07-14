package com.echovale.common.utils;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/7/14 20:33
 */

@Component
public class TimeUtils {

    public LocalDateTime long2LocalDateTime(Long longTime) {
        return Instant.ofEpochMilli(longTime)
                .atZone(ZoneId.of("Asia/Shanghai"))
                .toLocalDateTime();
    }
}
