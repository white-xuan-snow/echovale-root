package com.echovale.playlist.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/7/7 23:35
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("playlist_musics")
public class PlaylistMusicsPO {
    @MppMultiId
    private Long playlistId;
    @MppMultiId
    private Long musicId;
}
