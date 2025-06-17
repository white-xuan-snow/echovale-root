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

import java.sql.Timestamp;
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

        System.currentTimeMillis();


        // 音乐直链需要通过api获取
        List<MusicUrlDTO> musicUrlDTOList = musicApi.getMusicV1Url(neteaseIds, level);



        // 返回结果

        return List.of();
    }

    @Override
    public List<MusicModel> elicitMusic(List<Long> ids) throws Exception {

        // 查询


        return List.of();
    }


}
