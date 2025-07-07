package com.echovale.service.impl;

import com.echovale.domain.mapper.PlaylistMapper;
import com.echovale.domain.mapper.PlaylistMusicsMapper;
import com.echovale.domain.po.PlaylistMusicsPO;
import com.echovale.service.MusicOrchestrator;
import com.echovale.service.MusicService;
import com.echovale.service.dto.MusicDTO;
import com.echovale.domain.po.PlaylistPO;
import com.echovale.service.PlaylistService;
import com.echovale.service.mapping.PlaylistVOMapping;
import com.echovale.service.util.WrapperUtil;
import com.echovale.service.vo.PlaylistVO;
import com.netease.music.api.autoconfigure.configuration.api.MusicApi;
import com.netease.music.api.autoconfigure.configuration.pojo.result.PlaylistResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
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
    PlaylistMusicsMapper playlistMusicsMapper;
    @Autowired
    MusicOrchestrator musicOrchestrator;


    @Autowired
    PlaylistVOMapping playlistVOMapping;


    @Override
    public PlaylistVO elicitPlaylist(Long id, Long neteaseId) throws Exception {


        // 优先从数据库查找歌单
        if (id != 0) {
            return elicitPlaylistById(id);
        } else {
            return elicitPlaylistByNeteaseId(neteaseId);
        }
    }



    private PlaylistVO elicitPlaylistByNeteaseId(Long neteaseId) throws Exception {
        // 先从数据库中查询是否有列表

        PlaylistPO playlistPO = playlistMapper.selectJoinOne(wrapperUtil.getPlaylistWrapper(false)
                .eq(PlaylistPO::getNeteaseId, neteaseId));

        List<MusicDTO> musicDTOList = new ArrayList<>();



        // 没有就从api获取并保存到数据库
        if (playlistPO == null) {
            PlaylistResult playlist = musicApi.playlist(neteaseId.toString());

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
            playlistMapper.insertOrUpdate(playlistPO);

            // 更新歌曲相关信息
            musicDTOList = musicOrchestrator.updateMusics(playlist.getTracks());

        } else {

            musicDTOList = elicitMusicDTOListByPlaylistId(playlistPO.getId());

        }


        PlaylistVO playlistVO = playlistVOMapping.byPO(playlistPO);
        playlistVOMapping.addMusicDTOList(musicDTOList, playlistVO);

        return playlistVO;
    }


    private PlaylistVO elicitPlaylistById(Long id) throws Exception {
        PlaylistPO playlistPO = playlistMapper.selectJoinOne(wrapperUtil.getPlaylistWrapper(false)
                .eq(PlaylistPO::getId, id));

        List<MusicDTO> musicDTOList = elicitMusicDTOListByPlaylistId(playlistPO.getId());

        PlaylistVO playlistVO = playlistVOMapping.byPO(playlistPO);
        playlistVOMapping.addMusicDTOList(musicDTOList, playlistVO);

        return playlistVO;
    }

    // 调用MusicOrchestrator获取音乐信息
    private List<MusicDTO> elicitMusicDTOListByPlaylistId(Long playlistId) {

        // 获取音乐id List
        List<PlaylistMusicsPO> playlistMusicsPOList = playlistMusicsMapper.selectList(wrapperUtil.getPlaylistMusicsWrapper(playlistId));

        List<Long> musicIdList = playlistMusicsPOList.stream()
                .map(PlaylistMusicsPO::getMusicId)
                .toList();

        return musicOrchestrator.elicitMusicDTOList(musicIdList);
    }



}
