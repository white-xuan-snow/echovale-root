package com.echovale.music.domain.entity;

import com.echovale.music.domain.valueobject.MusicId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 15:42
 */
@Getter
@Builder
@AllArgsConstructor
public class MusicEntertainment {
    private final Long id;
    private final MusicId musicId;
    private final String content;
}
