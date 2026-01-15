package com.echovale.playlist.application.service.impl;

import com.echovale.music.domain.service.MusicSupplyService;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.playlist.api.vo.PlaylistVO;
import com.echovale.playlist.application.command.ElicitPlaylistCommand;
import com.echovale.playlist.application.dto.PlaylistDTO;
import com.echovale.playlist.application.query.PlaylistQueryService;
import com.echovale.playlist.application.service.PlaylistApplicationService;
import com.echovale.playlist.domain.aggregate.Playlist;
import com.echovale.playlist.domain.service.PlaylistSupplyService;
import com.echovale.playlist.domain.valueobject.PlaylistId;
import com.echovale.playlist.infrastructure.converter.PlaylistConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/1 10:40
 */
@Slf4j
@Service
public class PlaylistApplicationServiceImpl implements PlaylistApplicationService {

    @Autowired
    private PlaylistQueryService playlistQueryService;

    @Autowired
    private PlaylistSupplyService playlistSupplyService;

    @Autowired
    private PlaylistConverter playlistConverter;

    @Autowired
    private MusicSupplyService musicSupplyService;


    @Override
    public PlaylistVO elicitPlaylist(ElicitPlaylistCommand command) {

        PlaylistDTO playlistDTO;

        Playlist playlist;

        PlaylistId playlistId = PlaylistId.of(command.getPlaylistId());
        NeteaseId neteaseId = NeteaseId.byLong(command.getNeteaseId());

        if (command.isPlaylistIdNull()) {
            // 通过NeteaseId查询PlaylistId
            playlistId = playlistQueryService.queryId(neteaseId);
        }



        if (playlistId.isNull()) {
            // 从聚合服务查询
            playlistDTO = playlistSupplyService.getPlaylist(neteaseId);
        } else {
            // 从查询服务查询歌单信息
            playlist = playlistQueryService.queryPlaylist(playlistId);
            // 查询关联MusicIds
            List<MusicId> musicIds = playlistQueryService.queryMusicIds(playlistId, command.getPage(), command.getSize());

            // 从MusicSupplyService获取歌曲信息

//            List<MusicDTO> musicVO = musicSupplyService.getMusics(musicIds);


        }



        return null;
    }
}
