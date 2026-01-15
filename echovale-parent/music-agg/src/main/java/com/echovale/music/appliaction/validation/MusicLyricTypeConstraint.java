package com.echovale.music.appliaction.validation;


import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 30531
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MusicLyricTypeValidator.class)
public @interface MusicLyricTypeConstraint {
    String message() default "音乐歌词类型错误";

    Class<?>[] groups() default {};

    Class<? extends java.lang.annotation.
            ElementType>[] payload() default {};
}
