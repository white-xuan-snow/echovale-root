package com.echovale.service.impl;

import com.echovale.domain.mapper.PlaylistMapper;
import com.echovale.service.dto.MusicDTO;
import com.echovale.domain.po.PlaylistPO;
import com.echovale.service.MusicUpdateOrchestrator;
import com.echovale.service.PlaylistService;
import com.echovale.service.util.WrapperUtil;
import com.echovale.service.vo.PlaylistVO;
import com.netease.music.api.autoconfigure.configuration.api.MusicApi;
import com.netease.music.api.autoconfigure.configuration.pojo.result.PlaylistResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/12 15:26
 */

@Slf4j
@Service
public class PlaylistServiceImpl implements PlaylistService {


    @Autowired
    MusicApi musicApi;
    @Autowired
    WrapperUtil wrapperUtil;
    @Autowired
    PlaylistMapper playlistMapper;
    @Autowired
    MusicUpdateOrchestrator musicUpdateOrchestrator;


    @Override
    public PlaylistVO elicitPlaylist(Long id) throws Exception {

        // 先从数据库中查询是否有列表
        PlaylistPO playlistPO = playlistMapper.selectJoinOne(wrapperUtil.getPlaylistWrapper(false)
                .eq(PlaylistPO::getNeteaseId, id)
        );

        // 没有就从api获取并保存到数据库
        if (playlistPO == null) {
            PlaylistResult playlist = musicApi.playlist(id.toString());

            Timestamp createTime = new Timestamp(playlist.getCreateTime());
            Timestamp updateTime = new Timestamp(playlist.getUpdateTime());

            playlistPO = PlaylistPO.builder()
                    .name(playlist.getName())
                    .coverUrl(playlist.getCoverImgUrl())
                    .createTime(createTime.getTime())
                    .updateTime(updateTime.getTime())
                    .tags(playlist.getTags().toString())
                    .description(playlist.getDescription())
                    .isUser(false)
                    .neteaseId(playlist.getId())
                    .build();

            // 插入歌单
            playlistMapper.insert(playlistPO);

            // 更新歌曲相关信息
            List<MusicDTO> musicDTOList = musicUpdateOrchestrator.updateMusics(playlist.getTracks());


        }




        return null;
    }





}
