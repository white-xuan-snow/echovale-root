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
    public List<MusicUrlVO> elicitMusicUrl(List<String> ids, String level) throws Exception {

        // 音乐直链需要通过api获取
        List<MusicUrlDTO> musicUrlDTOList = musicApi.getMusicV1Url(ids, level);




        // 查询数据是否有该歌信息


        // 没有就再次通过api获取歌曲信息

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
        return getBaseWrapper();
    }
}
