package com.echovale.music.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/27 18:12
 */


@Value
@Builder
@AllArgsConstructor
public class MusicLyricRequest {
    Long id;
    Long neteaseId;
    List<String> types;
}
