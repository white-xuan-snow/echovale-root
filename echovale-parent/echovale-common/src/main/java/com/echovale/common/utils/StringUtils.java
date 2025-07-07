package com.echovale.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/7/7 22:58
 */
@Slf4j
@Component
public class StringUtils {


    public <T> String list2String(List<T> list) {
        return list == null ? "" : list.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }


    public <T> List<T> string2List(String str, Function<String, T> convertor) {
        return str == null ? new ArrayList<>() : Arrays.stream(str.split(","))
                .map(String::trim)
                .map(convertor)
                .toList();
    }

}
