package com.echovale.music.api.validation;

import com.echovale.music.api.constant.FieldConstant;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 20:59
 */
public class MusicLevelValidator implements ConstraintValidator<MusicLevelConstraint, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
        return FieldConstant.MusicLevel.ALL_LEVELS.contains(s.trim().toLowerCase());
    }
}
