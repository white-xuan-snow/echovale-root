package com.echovale.music.appliaction.validation;

import com.echovale.music.appliaction.constant.FieldConstant;
import com.echovale.music.domain.entity.Lyric;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/28 14:28
 */
public class MusicLyricTypeValidator implements ConstraintValidator<MusicLyricTypeConstraint, List<String>> {
    @Override
    public boolean isValid(List<String> types, ConstraintValidatorContext constraintValidatorContext) {
        if (types == null || types.isEmpty()) {
            return false;
        }
        return Lyric.LyricType.ALL_LYRIC_TYPES.containsAll(types);
    }
}
