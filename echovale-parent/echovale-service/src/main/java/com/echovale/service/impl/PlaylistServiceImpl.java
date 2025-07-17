package com.echovale.service.impl;

import com.echovale.common.utils.TimeUtils;
import com.echovale.domain.mapper.PlaylistMapper;
import com.echovale.domain.mapper.PlaylistMusicsMapper;
import com.echovale.domain.po.PlaylistMusicsPO;
import com.echovale.service.MusicOrchestrator;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    TimeUtils timeUtils;


    @Autowired
    PlaylistVOMapping playlistVOMapping;


    /** 
     * @description: 解析歌单 
     * @param: id
neteaseId 
     * @return: com.echovale.service.vo.PlaylistVO 
     * @author 30531
     * @date: 2025/7/16 16:04
     */ 
    @Override
    @Transactional
    public PlaylistVO elicitPlaylist(Long id, Long neteaseId) throws Exception {


        // 优先从数据库查找歌单
        if (id != null) {
            return elicitPlaylistById(id);
        } else {
            return elicitPlaylistByNeteaseId(neteaseId);
        }
    }


    /** 
     * @description: 通过网易云歌单id解析歌单 
     * @param: neteaseId 
     * @return: com.echovale.service.vo.PlaylistVO 
     * @author 30531
     * @date: 2025/7/16 16:04
     */ 
    private PlaylistVO elicitPlaylistByNeteaseId(Long neteaseId) throws Exception {
        // 先从数据库中查询是否有列表

        PlaylistPO playlistPO = playlistMapper.selectJoinOne(wrapperUtil.getPlaylistWrapper(false)
                .eq(PlaylistPO::getNeteaseId, neteaseId));

        List<MusicDTO> musicDTOList = new ArrayList<>();



        // 没有就从api获取并保存到数据库
        if (playlistPO == null) {
            PlaylistResult playlist = musicApi.playlist(neteaseId.toString());


            LocalDateTime createTime = timeUtils.long2LocalDateTime(System.currentTimeMillis());
            LocalDateTime updateTime = timeUtils.long2LocalDateTime(System.currentTimeMillis());

            playlistPO = PlaylistPO.builder()
                    .name(playlist.getName())
                    .coverUrl(playlist.getCoverImgUrl())
                    .createTime(createTime)
                    .updateTime(updateTime)
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

        // 更新歌单音乐关联表
        updatePlaylistMusicsByDTO(playlistPO.getId(), musicDTOList);

        return playlistVO;
    }

    /** 
     * @description:  通过歌单id解析歌单
     * @param: id 
     * @return: com.echovale.service.vo.PlaylistVO 
     * @author 30531
     * @date: 2025/7/16 16:04
     */ 
    private PlaylistVO elicitPlaylistById(Long id) throws Exception {
        PlaylistPO playlistPO = playlistMapper.selectJoinOne(wrapperUtil.getPlaylistWrapper(false)
                .eq(PlaylistPO::getId, id));

        // 查询音乐信息
        List<MusicDTO> musicDTOList = elicitMusicDTOListByPlaylistId(playlistPO.getId());

        PlaylistVO playlistVO = playlistVOMapping.byPO(playlistPO);
        playlistVOMapping.addMusicDTOList(musicDTOList, playlistVO);

        // 通过id获取的playlist不需要更新歌单音乐关联表
//        updatePlaylistMusicsByDTO(playlistPO.getId(), musicDTOList);

        return playlistVO;
    }

    /** 
     * @description:  调用MusicOrchestrator获取音乐信息
     * @param: playlistId 
     * @return: java.util.List<com.echovale.service.dto.MusicDTO> 
     * @author 30531
     * @date: 2025/7/16 16:05
     */ 
    private List<MusicDTO> elicitMusicDTOListByPlaylistId(Long playlistId) throws Exception {

        // 获取音乐id List
        List<PlaylistMusicsPO> playlistMusicsPOList = playlistMusicsMapper.selectList(wrapperUtil.getPlaylistMusicsWrapper(playlistId));

        List<Long> musicIdList = playlistMusicsPOList.stream()
                .map(PlaylistMusicsPO::getMusicId)
                .toList();

        return musicOrchestrator.elicitMusicDTOList(musicIdList);
    }

    /** 
     * @description: 使用List<MusicDTO>更新歌单歌曲关联表
     * @param: playlistId
musicDTOList 
     * @return: void 
     * @author 30531
     * @date: 2025/7/16 16:06
     */ 
    private void updatePlaylistMusicsByDTO(Long playlistId, List<MusicDTO> musicDTOList) {
        List<Long> musicIdList = musicDTOList.stream()
                .map(MusicDTO::getId)
                .toList();
        updatePlaylistMusicsById(playlistId, musicIdList);
    }

    /** 
     * @description: 使用List<歌曲id>更新歌单歌曲关联表
     * @param: playlistId
    musicIdList 
     * @return: void 
     * @author 30531
     * @date: 2025/7/16 16:06
     */ 
    private void updatePlaylistMusicsById(Long playlistId, List<Long> musicIdList) {

        List<PlaylistMusicsPO> playlistMusicsPOList = musicIdList.stream()
                .map(o -> PlaylistMusicsPO.builder()
                        .playlistId(playlistId)
                        .musicId(o)
                        .build())
                .toList();
        
        insertPlaylistMusicsByPO(playlistMusicsPOList);
    }

    /** 
     * @description: 使用List<MusicPO>更新歌单歌曲关联表
     * @param: playlistMusicsPOList 
     * @return: void 
     * @author 30531
     * @date: 2025/7/16 19:44
     */ 
    private void insertPlaylistMusicsByPO(List<PlaylistMusicsPO> playlistMusicsPOList) {
        playlistMusicsMapper.insert(playlistMusicsPOList);
    }



}
