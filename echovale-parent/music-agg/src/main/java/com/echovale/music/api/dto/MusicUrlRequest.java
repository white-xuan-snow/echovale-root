package com.echovale.music.api.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 21:07
 */
@Value
@Builder
public class MusicUrlRequest {
    List<Long> ids;
    List<Long> neteaseIds;
    String level;

}
