package com.echovale.music.appliaction.validation;

import com.echovale.music.appliaction.constant.FieldConstant;
import com.echovale.music.domain.entity.MusicQuality;
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
        return MusicQuality.MusicLevel.ALL_LEVELS.contains(s.trim().toLowerCase());
    }
}
