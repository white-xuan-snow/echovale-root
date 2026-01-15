package com.echovale.common.domain.api.utils;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/14 21:06
 */

@Component
public class StreamUtils {

    /**
     * 创建一个 Predicate，用于 Stream 的 filter 操作，根据 keyExtractor 提供的唯一键进行去重
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        // 使用 ConcurrentHashMap 的 keySet 集合来存储和检查已出现的键
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        // 核心逻辑：如果 add(key) 返回 false，说明 key 已存在，则 filter 过滤掉它 (返回 false)
        return t -> seen.add(keyExtractor.apply(t));
    }
}
