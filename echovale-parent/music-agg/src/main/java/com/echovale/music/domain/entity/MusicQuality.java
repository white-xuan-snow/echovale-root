package com.echovale.music.domain.entity;

import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.QualityLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 15:37
 */

@Getter
@Builder
@AllArgsConstructor
public class MusicQuality {
    private final Long id;
    private final MusicId musicId;
    private final QualityLevel level;
    private final Integer br;
    private final Integer sr;
    private final Integer size;
}
