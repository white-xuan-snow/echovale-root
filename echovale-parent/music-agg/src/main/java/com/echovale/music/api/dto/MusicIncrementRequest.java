package com.echovale.music.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/18 11:50
 */
@Value
@Builder
@AllArgsConstructor
public class MusicIncrementRequest {
    @NonNull
    Long id;
}
