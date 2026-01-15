package com.echovale.playlist.application.query;


import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.playlist.application.dto.PlaylistDTO;
import com.echovale.playlist.domain.aggregate.Playlist;
import com.echovale.playlist.domain.valueobject.PlaylistId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlaylistQueryService {
    Playlist queryPlaylist(PlaylistId playlistId);

    PlaylistId queryId(NeteaseId neteaseId);

    List<MusicId> queryMusicIds(PlaylistId playlistId, Integer page, Integer size);
}
