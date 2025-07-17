package com.echovale.common.constants.param;

import com.netease.music.api.autoconfigure.configuration.module.MusicModule;

import java.util.HashSet;
import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/7/16 20:06
 */
public class SearchParam {
    public static final Integer Limit = 30;
    public static final Integer Offset = 0;
    public static final Integer Type = MusicModule.Type.Single;
    public static final HashSet<Integer> TypeSet = new HashSet<>(List.of(
            MusicModule.Type.All, MusicModule.Type.Single, MusicModule.Type.Author,
            MusicModule.Type.Album, MusicModule.Type.Lyric, MusicModule.Type.PlayList,
            MusicModule.Type.DJ, MusicModule.Type.MV, MusicModule.Type.Video
    ));
}
