package com.echovale.music.infrastructure.gateway;

import com.echovale.music.api.vo.MusicUrlDetailVO;
import com.echovale.music.domain.gateway.MusicApiGateway;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.converter.MusicUrlDetailVOConverter;
import com.echovale.shared.external.gateway.MusicNeteaseApiGateway;
import com.netease.music.api.autoconfigure.configuration.pojo.result.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 17:11
 */

@Slf4j
@Service("MusicUrlNeteaseProvider")
public class MusicApiGatewayImpl implements MusicApiGateway {

    @Autowired
    private MusicNeteaseApiGateway neteaseApiGateway;

    @Autowired
    private MusicUrlDetailVOConverter musicUrlDetailVOConverter;


    @Override
    public List<MusicUrlDetailVO> elicitMusicUrl(List<MusicId> musicIdList, List<NeteaseId> neteaseIdList, String level) {

        List<Long> apiIdList = neteaseIdList.stream()
                .map(NeteaseId::getId)
                .toList();


        List<MusicUrlResult> musicUrlResults = neteaseApiGateway.getMusicV1Url(apiIdList, level);

        List<MusicUrlDetailVO> musicUrlDetailVOList = new ArrayList<>();

        for (int i = 0; i < musicUrlResults.size(); i++) {
            musicUrlDetailVOList.add(musicUrlDetailVOConverter.byResult(musicUrlResults.get(i), musicIdList.get(i)));
        }


        return musicUrlDetailVOList;
    }


    @Override
    public MusicDetailResult elicitMusic(NeteaseId neteaseId) {

        List<MusicDetailResult> detail = neteaseApiGateway.getMusicDetail(List.of(neteaseId.getId()));

        return detail.get(0);
    }

    @Override
    public List<AuthorDetailResult> elicitMusicAuthors(NeteaseId neteaseId)  {

        MusicDetailResult musicDetailResult = elicitMusic(neteaseId);

        return musicDetailResult.getAr();
    }

    @Override
    public AlbumResult elicitAlbum(NeteaseId neteaseId) {

        AlbumListResult albumListResult = elicitAlbumList(neteaseId);

        return albumListResult.getAlbum();
    }

    @Override
    public AlbumListResult elicitAlbumList(NeteaseId neteaseId) {

        MusicDetailResult musicDetailResult = elicitMusic(neteaseId);

        NeteaseId albumNeteaseId = new NeteaseId(musicDetailResult.getAl().getId());

        AlbumListResult albumListResult = neteaseApiGateway.album(albumNeteaseId.getId());

        return albumListResult;
    }

    @Override
    public ChorusResult elicitChorus(NeteaseId neteaseId) {

        List<ChorusResult> chorusResults = elicitChoruses(List.of(neteaseId));

        return chorusResults.get(0);
    }

    @Override
    public List<ChorusResult> elicitChoruses(List<NeteaseId> neteaseIdList) {

        if (neteaseIdList.isEmpty()) {
            return List.of();
        }

        List<ChorusResult> chorus = neteaseApiGateway.getChorus(NeteaseId.getLongList(neteaseIdList));

        return chorus;
    }

    @Override
    public LyricsResult elicitLyrics(NeteaseId neteaseId) {

        LyricsResult lyrics = neteaseApiGateway.getLyrics(neteaseId.getId());

        return lyrics;
    }

    @Override
    public List<MusicDetailResult> elicitMusics(List<NeteaseId> nonexistentMusicNeteaseIdList) {
        if (CollectionUtils.isEmpty(nonexistentMusicNeteaseIdList)) {
            return List.of();
        }

        return neteaseApiGateway.getMusicDetail(NeteaseId.getLongList(nonexistentMusicNeteaseIdList));
    }
}
