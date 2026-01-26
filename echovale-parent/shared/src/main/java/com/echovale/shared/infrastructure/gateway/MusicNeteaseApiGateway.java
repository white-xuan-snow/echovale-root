package com.echovale.shared.infrastructure.gateway;


import com.netease.music.api.autoconfigure.configuration.pojo.result.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 30531
 */
@Service
public interface MusicNeteaseApiGateway {


    List<MusicDetailResult> getMusicDetail(List<Long> musicIds);

    AlbumListResult album(Long id);

    List<ChorusResult> getChorus(List<Long> neteaseIdList);

    LyricsResult getLyrics(Long id);

    List<MusicUrlResult> getMusicV1Url(List<Long> apiIdList, String level);

    PlaylistResult getPlaylist(Long neteaseId);
}
