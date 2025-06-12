package com.echovale.service.impl;

import com.echovale.domain.mapper.MusicMapper;
import com.echovale.domain.model.MusicModel;
import com.echovale.domain.po.*;
import com.echovale.service.MusicService;
import com.echovale.service.vo.MusicUrlVO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.netease.music.api.autoconfigure.configuration.api.MusicApi;
import com.netease.music.api.autoconfigure.configuration.pojo.dto.MusicUrlDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/10 14:28
 */

@Service
public class MusicServiceImpl implements MusicService {


    @Autowired
    MusicApi musicApi;

    @Autowired
    MusicMapper musicMapper;

    @Override
    public List<MusicUrlVO> elicitMusicUrl(List<Long> ids, String level) throws Exception {

        // 从数据库中查询neteaseIds

        List<String> neteaseIds = musicMapper.selectJoinList(String.class,
                new MPJLambdaWrapper<MusicPO>()
                        .select(MusicPO::getNeteaseId)
                        .in(MusicPO::getId, ids)
        );

        // 音乐直链需要通过api获取
        List<MusicUrlDTO> musicUrlDTOList = musicApi.getMusicV1Url(neteaseIds, level);



        // 返回结果

        return List.of();
    }



    private MPJLambdaWrapper<MusicPO> getBaseWrapper() {
        return new MPJLambdaWrapper<MusicPO>()
                .selectAll(MusicPO.class)
                .leftJoin(AlbumPO.class, AlbumPO::getId, MusicPO::getAlbumId)
                .leftJoin(MusicAuthors.class, MusicAuthors::getMusicId, MusicPO::getId)
                .leftJoin(AuthorPO.class, AuthorPO::getId, MusicAuthors::getAuthorId)
                .leftJoin(MusicAwardsPO.class, MusicAwardsPO::getMusicId, MusicPO::getId)
                .leftJoin(MusicEntertainmentPO.class, MusicEntertainmentPO::getMusicId, MusicPO::getId)
                .selectAssociation(AlbumPO.class, MusicModel::getAlbum)
                .selectCollection(AuthorPO.class, MusicModel::getAuthors)
                .selectCollection(MusicAwardsPO.class, MusicModel::getAwards)
                .selectCollection(MusicEntertainmentPO.class, MusicModel::getEntertainments);
    }

    private MPJLambdaWrapper<MusicPO> getEntireWrapper() {
        return getBaseWrapper()
                .leftJoin(MusicInfoExtendPO.class, MusicInfoExtendPO::getMusicId, MusicPO::getId)
                .leftJoin(MusicSheetsPO.class, MusicSheetsPO::getMusicId, MusicPO::getId)
                .leftJoin(LyricPO.class, LyricPO::getMusicId, MusicPO::getId)
                .leftJoin(MusicStylesPO.class, MusicStylesPO::getMusicId, MusicPO::getId)
                .leftJoin(MusicTagsPO.class, MusicTagsPO::getMusicId, MusicPO::getId)
                .leftJoin(MusicLanguages.class, MusicLanguages::getMusicId, MusicPO::getId)
                .leftJoin(StylePO.class, StylePO::getId, MusicStylesPO::getStyleId)
                .leftJoin(TagPO.class, TagPO::getId, MusicTagsPO::getTagId)
                .leftJoin(Language.class, Language::getId, MusicLanguages::getLanguageId)
                .selectAssociation(LyricPO.class, MusicModel::getLyric)
                .selectAssociation(MusicInfoExtendPO.class, MusicModel::getInfo)
                .selectCollection(StylePO.class, MusicModel::getStyles)
                .selectCollection(TagPO.class, MusicModel::getTags)
                .selectCollection(Language.class, MusicModel::getLanguages)
                .selectCollection(MusicSheetsPO.class, MusicModel::getSheets);
    }
}
