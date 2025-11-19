package com.echovale.music.api.vo;

import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.QualityLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/19 21:10
 */

@Value
@Builder
@AllArgsConstructor
public class MusicQualityVO {
    Long id;
    MusicId musicId;
    QualityLevel level;
    Integer br;
    Integer sr;
    Integer size;
}
