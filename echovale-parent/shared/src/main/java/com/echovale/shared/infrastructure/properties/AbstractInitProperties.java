package com.echovale.shared.infrastructure.properties;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.util.TypeUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/28 16:48
 */
@Slf4j
public abstract class AbstractInitProperties {


    @PostConstruct
    protected void init() {
        autoSyncFixedStaticFields();
        manualSyncCustomStaticFields();
        logStaticFields();
    }

    private void logStaticFields() {
        Class<?> clazz = this.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                try {
                    field.setAccessible(true);
                    log.info("初始化：{} -> {}", field.getName(), field.get(null));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    protected void manualSyncCustomStaticFields() {}

    private void autoSyncFixedStaticFields() {
        Class<?> clazz = this.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                try {
                    String staticFieldName = PropertyNamingStrategy.SnakeCase.translate(field.getName()).toUpperCase();
                    Field staticField = clazz.getDeclaredField(staticFieldName);
                    staticField.setAccessible(true);
                    if (staticField.get(this) == null) {
                        field.setAccessible(true);
                        staticField.set(null, field.get(this));
                    }
                } catch (NoSuchFieldException e) {
                    log.warn("当前字段不存在静态字段：{}", field.getName());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
