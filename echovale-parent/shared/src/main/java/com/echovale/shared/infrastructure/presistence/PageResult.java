package com.echovale.shared.infrastructure.presistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/9 08:16
 */
@Value
@Builder
@AllArgsConstructor
public class PageResult<T> {
    List<T> items;
    long total;
    int page;
    int size;
    int pages;
}
