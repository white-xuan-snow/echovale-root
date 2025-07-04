package com.echovale.service.util;

import com.echovale.service.dto.MusicDTO;
import com.echovale.domain.po.*;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Component;

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
                .leftJoin(MusicAuthorsPO.class, MusicAuthorsPO::getMusicId, MusicPO::getId)
                .leftJoin(AuthorPO.class, AuthorPO::getId, MusicAuthorsPO::getAuthorId)
                .leftJoin(MusicAwardsPO.class, MusicAwardsPO::getMusicId, MusicPO::getId)
                .leftJoin(MusicEntertainmentPO.class, MusicEntertainmentPO::getMusicId, MusicPO::getId)
                .selectAssociation(AlbumPO.class, MusicDTO::getAlbum)
                .selectCollection(AuthorPO.class, MusicDTO::getAuthors)
                .selectCollection(MusicAwardsPO.class, MusicDTO::getAwards)
                .selectCollection(MusicEntertainmentPO.class, MusicDTO::getEntertainments);
    }

    public MPJLambdaWrapper<MusicPO> getMusicEntireWrapper() {
        return getMusicBaseWrapper()
                .leftJoin(MusicInfoExtendPO.class, MusicInfoExtendPO::getMusicId, MusicPO::getId)
                .leftJoin(MusicSheetsPO.class, MusicSheetsPO::getMusicId, MusicPO::getId)
                .leftJoin(LyricPO.class, LyricPO::getMusicId, MusicPO::getId)
                .leftJoin(MusicStylesPO.class, MusicStylesPO::getMusicId, MusicPO::getId)
                .leftJoin(MusicTagsPO.class, MusicTagsPO::getMusicId, MusicPO::getId)
                .leftJoin(MusicLanguagesPO.class, MusicLanguagesPO::getMusicId, MusicPO::getId)
                .leftJoin(StylePO.class, StylePO::getId, MusicStylesPO::getStyleId)
                .leftJoin(TagPO.class, TagPO::getId, MusicTagsPO::getTagId)
                .leftJoin(LanguagePO.class, LanguagePO::getId, MusicLanguagesPO::getLanguageId)
                .selectAssociation(LyricPO.class, MusicDTO::getLyric)
                .selectAssociation(MusicInfoExtendPO.class, MusicDTO::getInfo)
                .selectCollection(StylePO.class, MusicDTO::getStyles)
                .selectCollection(TagPO.class, MusicDTO::getTags)
                .selectCollection(LanguagePO.class, MusicDTO::getLanguagePOS)
                .selectCollection(MusicSheetsPO.class, MusicDTO::getSheets);
    }

    public MPJLambdaWrapper<PlaylistPO> getPlaylistWrapper(Boolean isUser) {
        return new MPJLambdaWrapper<PlaylistPO>()
                .selectAll(PlaylistPO.class)
                .eq(PlaylistPO::getIsUser, isUser);
    }




}
