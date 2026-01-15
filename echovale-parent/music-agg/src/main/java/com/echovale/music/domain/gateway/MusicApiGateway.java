package com.echovale.music.domain.gateway;

import com.echovale.music.api.vo.MusicUrlDetailVO;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.netease.music.api.autoconfigure.configuration.pojo.result.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/18 21:02
 */

@Service
public interface MusicApiGateway {
    List<MusicUrlDetailVO> elicitMusicUrl(List<MusicId> musicIdList, List<NeteaseId> neteaseIdList, String level);


    MusicDetailResult elicitMusic(NeteaseId neteaseId);

    List<AuthorDetailResult> elicitMusicAuthors(NeteaseId neteaseId);

    AlbumResult elicitAlbum(NeteaseId neteaseId);

    AlbumListResult elicitAlbumList(NeteaseId neteaseId);

    ChorusResult elicitChorus(NeteaseId neteaseId);

    List<ChorusResult> elicitChoruses(List<NeteaseId> neteaseIdList);

    LyricsResult elicitLyrics(NeteaseId neteaseId);

    List<MusicDetailResult> elicitMusics(List<NeteaseId> nonexistentMusicNeteaseIdList);
}
