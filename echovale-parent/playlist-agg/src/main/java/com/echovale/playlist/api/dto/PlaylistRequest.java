package com.echovale.playlist.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/2 08:17
 */


@Value
@Builder
@AllArgsConstructor
public class PlaylistRequest {
    Long playlistId;
    Long neteaseId;
    Integer page;
    Integer size;
}
