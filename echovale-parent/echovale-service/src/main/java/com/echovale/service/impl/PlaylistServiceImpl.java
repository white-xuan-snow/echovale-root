package com.echovale.service.impl;

import com.echovale.domain.mapper.PlaylistMapper;
import com.echovale.domain.po.PlaylistPO;
import com.echovale.service.PlaylistService;
import com.echovale.service.util.WrapperUtil;
import com.echovale.service.vo.PlaylistVO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.netease.music.api.autoconfigure.configuration.api.MusicApi;
import com.netease.music.api.autoconfigure.configuration.pojo.dto.PlaylistDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/12 15:26
 */
@Service
public class PlaylistServiceImpl implements PlaylistService {


    @Autowired
    MusicApi musicApi;
    @Autowired
    WrapperUtil wrapperUtil;
    @Autowired
    PlaylistMapper playlistMapper;


    @Override
    public PlaylistVO elicitPlaylist(Long id) throws Exception {

        // 先从数据库中查询是否有列表
        PlaylistPO playlistPO = playlistMapper.selectJoinOne(wrapperUtil.getPlaylistWrapper(false)
                .eq(PlaylistPO::getId, id)
        );

        // 没有就从api获取并保存到数据库
        if (playlistPO == null) {
            PlaylistDTO playlist = musicApi.playlist(id.toString());

            playlistPO = PlaylistPO.builder()
                    .name(playlist.getName())
                    .coverUrl(playlist.getCoverImgUrl())
                    .createTime(playlist.getCreateTime())
                    .updateTime(playlist.getUpdateTime())
                    .tags(playlist.getTags().toString())
                    .description(playlist.getDescription())
                    .isUser(false)
                    .neteaseId(Long.parseLong(playlist.getId()))
                    .build();

            // 插入歌单
            playlistMapper.insert(playlistPO);


        }




        return null;
    }





}
