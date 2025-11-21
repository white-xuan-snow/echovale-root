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
    List<MusicUrlDetailVO> elicitMusicUrl(List<MusicId> musicIdList, List<NeteaseId> neteaseIdList, String level) throws Exception;


    MusicDetailResult elicitMusic(NeteaseId neteaseId) throws Exception;

    List<AuthorDetailResult> elicitMusicAuthors(NeteaseId neteaseId) throws Exception;

    AlbumResult elicitAlbum(NeteaseId neteaseId) throws Exception;

    AlbumListResult elicitAlbumList(NeteaseId neteaseId) throws Exception;

    ChorusResult elicitChorus(NeteaseId neteaseId) throws Exception;

    List<ChorusResult> elicitChoruses(List<NeteaseId> neteaseIdList) throws Exception;
}
