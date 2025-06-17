package com.echovale.service.util;

import com.echovale.domain.model.MusicModel;
import com.echovale.domain.po.*;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.bouncycastle.util.Times;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/15 18:03
 */

@Component
public class WrapperUtil {
    public MPJLambdaWrapper<MusicPO> getMusicBaseWrapper() {
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

    public MPJLambdaWrapper<MusicPO> getMusicEntireWrapper() {
        return getMusicBaseWrapper()
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

    public MPJLambdaWrapper<PlaylistPO> getPlaylistWrapper(Boolean isUser) {
        return new MPJLambdaWrapper<PlaylistPO>()
                .selectAll(PlaylistPO.class)
                .eq(PlaylistPO::getIsUser, isUser);
    }




}
