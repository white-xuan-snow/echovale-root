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
@Constraint(validatedBy = MusicLevelValidator.class)
public @interface MusicLevelConstraint {
    String message() default "音乐等级错误";

    Class<?>[] groups() default {};

    Class<? extends java.lang.annotation.
            ElementType>[] payload() default {};
}
