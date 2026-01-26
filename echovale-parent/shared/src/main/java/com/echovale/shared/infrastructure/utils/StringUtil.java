package com.echovale.shared.infrastructure.utils;

import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/18 16:01
 */

@Component
public class StringUtil {

    /**
     * @description: 仅适用于传入已知的字符串，不适用于不确定的字符串，比如创建用户时的用户名等
     * @param: major minor
     * @return: java.lang.String
     * @author 30531
     * @date: 2026/1/18 16:09
     */
    public static String oneOf(String major, String minor) {
        if (!StringUtils.isBlank(major)) {
            return major;
        } else if (StringUtils.isBlank(minor)) {
            throw new IllegalArgumentException("没找到合适的参数");
        } else {
            return minor;
        }
    }

}
