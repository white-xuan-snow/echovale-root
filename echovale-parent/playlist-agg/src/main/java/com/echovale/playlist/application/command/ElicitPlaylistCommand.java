package com.echovale.playlist.application.command;

import com.echovale.playlist.domain.valueobject.PlaylistId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/2 08:15
 */




@Value
@Builder
@AllArgsConstructor
public class ElicitPlaylistCommand {
    Long playlistId;
    Long neteaseId;
    Integer page;
    Integer size;


    public Boolean isPlaylistIdNull() {
        return playlistId == null;
    }

}
