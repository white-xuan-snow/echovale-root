package com.echovale.playlist.application.dto;

import com.echovale.common.domain.infrastructure.presistence.PageResult;
import com.echovale.music.domain.aggregate.Music;
import com.echovale.playlist.domain.aggregate.Playlist;
import lombok.*;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/3 14:11
 */


@Value
@Builder
@AllArgsConstructor
public class PlaylistDTO {
    Playlist playlist;
    List<Music> musicList;
}
