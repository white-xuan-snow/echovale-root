package com.echovale.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Builder;
import lombok.Data;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/7/7 23:35
 */
@Data
@Builder
@TableName("playlist_musics")
public class PlaylistMusicsPO {
    @TableId
    @MppMultiId
    private Long playlistId;
    @MppMultiId
    private Long musicId;
}
