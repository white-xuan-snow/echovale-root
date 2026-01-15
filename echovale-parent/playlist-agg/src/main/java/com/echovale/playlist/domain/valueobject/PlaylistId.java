package com.echovale.playlist.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/1 10:43
 */

@Value
@Builder
@AllArgsConstructor
public class PlaylistId {
    @JsonValue
    Long id;


    public PlaylistId() {
        this.id = 0L;
    }

    public static PlaylistId of(Long playlistId) {
        if (playlistId == null) {
            return new PlaylistId();
        }
        return new PlaylistId(playlistId);
    }

    public Boolean isNull() {
        return id == null || id == 0L;
    }
}
