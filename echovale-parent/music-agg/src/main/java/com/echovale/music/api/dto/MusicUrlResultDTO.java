package com.echovale.music.api.dto;

import lombok.Builder;
import lombok.Value;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 19:26
 */

@Value
@Builder
public class MusicUrlResultDTO {
    Long id;
    Long neteaseId;
    String url;
    String status;
}
