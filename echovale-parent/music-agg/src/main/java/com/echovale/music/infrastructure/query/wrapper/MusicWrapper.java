package com.echovale.music.infrastructure.query.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.echovale.music.appliaction.dto.MusicIdMapping;
import com.echovale.music.domain.valueobject.AuthorId;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.po.*;
import com.github.yulichang.interfaces.MPJBaseJoin;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 20:06
 */

@Component
public class MusicWrapper {


    public MPJLambdaWrapper<MusicPO> queryMusicIdsWrapper() {
        return baseJoinMusicWrapper()
                .selectAs(MusicPO::getId, MusicIdMapping::getId)
                .selectAs(MusicPO::getNeteaseId, MusicIdMapping::getNeteaseId);
    }

    public MPJLambdaWrapper<MusicPO> queryMusicIdByNeteaseIdWrapper(Long neteaseId) {
        return baseJoinMusicWrapper()
                .select(MusicPO::getId)
                .eq(MusicPO::getNeteaseId, neteaseId);
    }








    public MPJLambdaWrapper<MusicPO> baseJoinMusicWrapper() {
        return new MPJLambdaWrapper<>(MusicPO.class);
    }


    public MPJLambdaWrapper<AuthorPO> baseAuthorWrapper() {
        return new MPJLambdaWrapper<>(AuthorPO.class);
    }

    public MPJLambdaWrapper<MusicPO> queryMusicByIdsWrapper(MusicId musicId, NeteaseId neteaseId) {

        return baseJoinMusicWrapper()
                .eq(MusicPO::getId, musicId.getId())
                .or()
                .eq(MusicPO::getNeteaseId, neteaseId.getId());

    }

    public MPJLambdaWrapper<MusicPO> queryNeteaseIdByIdWrapper(MusicId id) {
        return baseJoinMusicWrapper()
                .select(MusicPO::getNeteaseId)
                .eq(MusicPO::getId, id.getId());
    }

    public MPJLambdaWrapper<MusicQualitiesPO> queryMusicQualitiesWrapper(MusicId musicId) {
        return baseMusicQualitiesJoinWrapper()
                .eq(MusicQualitiesPO::getMusicId, musicId.getId());
    }


    public MPJLambdaWrapper<MusicQualitiesPO> baseMusicQualitiesJoinWrapper() {
        return new MPJLambdaWrapper<>(MusicQualitiesPO.class);
    }

    public LambdaQueryWrapper<MusicQualitiesPO> baseMusicQualitiesQueryWrapper() {
        return new LambdaQueryWrapper<>();
    }

    public MPJLambdaWrapper<LyricPO> queryMusicLyricsByIdWrapper(MusicId id) {
        return baseLyricWrapper()
                .eq(LyricPO::getMusicId, id.getId());
    }



    public MPJLambdaWrapper<LyricPO> baseLyricWrapper() {
        return new MPJLambdaWrapper<>(LyricPO.class);
    }

    public MPJLambdaWrapper<MusicPO> queryExistentNeteaseIds(List<NeteaseId> neteaseIds) {

        return baseJoinMusicWrapper()
                .select(MusicPO::getNeteaseId)
                .in(MusicPO::getNeteaseId, NeteaseId.getLongList(neteaseIds));
    }



    public LambdaQueryWrapper<MusicPO> baseQueryWrapper() {
        return new LambdaQueryWrapper<>();
    }

    public LambdaQueryWrapper<MusicPO> queryMusicsByMusicIdsWrapper(List<MusicId> musicIds) {
        return baseQueryWrapper()
                .in(MusicPO::getId, MusicId.toLongList(musicIds));


    }

    public LambdaQueryWrapper<MusicQualitiesPO> queryMusicQualitiesListByMusicIdsWrapper(List<MusicId> musicIds) {
        return baseMusicQualitiesQueryWrapper()
                .in(MusicQualitiesPO::getMusicId, MusicId.toLongList(musicIds));

    }


    public LambdaQueryWrapper<MusicAuthorsPO> baseMusicAuthorsQueryWrapper() {
        return new LambdaQueryWrapper<>();
    }

    public LambdaQueryWrapper<MusicAuthorsPO> queryAuthorIdsByMusicIdsWrapper(List<MusicId> musicIds) {
        return baseMusicAuthorsQueryWrapper()
                .in(MusicAuthorsPO::getMusicId, MusicId.toLongList(musicIds));

    }

    public MPJLambdaWrapper<MusicPO> queryExistentMusicIds(List<NeteaseId> neteaseIds) {
        return baseJoinMusicWrapper()
                .select(MusicPO::getId)
                .in(MusicPO::getNeteaseId, NeteaseId.getLongList(neteaseIds));
    }
}
