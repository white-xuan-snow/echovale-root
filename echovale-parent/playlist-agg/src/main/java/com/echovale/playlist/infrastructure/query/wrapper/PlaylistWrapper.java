package com.echovale.playlist.infrastructure.query.wrapper;

import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.playlist.domain.valueobject.PlaylistId;
import com.echovale.playlist.infrastructure.po.PlaylistMusicsPO;
import com.echovale.playlist.infrastructure.po.PlaylistPO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/3 14:43
 */

@Component
public class PlaylistWrapper {


    public MPJLambdaWrapper<PlaylistPO> queryPlaylistById(PlaylistId playlistId) {
        return playlistBaseWrapper()
                .eq(PlaylistPO::getId, playlistId.getId());
    }


    public MPJLambdaWrapper<PlaylistPO> playlistBaseWrapper() {
        return new MPJLambdaWrapper<>(PlaylistPO.class);
    }


    public MPJLambdaWrapper<PlaylistMusicsPO> queryPlaylistMusicsById(PlaylistId playlistId) {
        return playlistMusicsBaseWrapper()
                .eq(PlaylistMusicsPO::getPlaylistId, playlistId.getId());
    }




    public MPJLambdaWrapper<PlaylistMusicsPO> playlistMusicsBaseWrapper() {
        return new MPJLambdaWrapper<>(PlaylistMusicsPO.class);
    }

    public MPJLambdaWrapper<PlaylistPO> queryIdByNeteaseId(NeteaseId neteaseId) {
        return playlistBaseWrapper()
                .eq(PlaylistPO::getNeteaseId, neteaseId.getId())
                .select(PlaylistPO::getId);
    }
}
