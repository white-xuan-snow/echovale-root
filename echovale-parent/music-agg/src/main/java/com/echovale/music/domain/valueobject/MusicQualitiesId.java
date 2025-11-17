package com.echovale.music.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 15:19
 */

@Value
@AllArgsConstructor
public class MusicQualitiesId {
    private Integer level;
    private Integer br;
    private Integer sr;
    private Integer size;
}
