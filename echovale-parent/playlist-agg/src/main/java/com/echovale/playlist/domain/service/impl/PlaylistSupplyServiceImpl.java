package com.echovale.playlist.domain.service.impl;

import com.echovale.music.domain.aggregate.Music;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.converter.MusicConverter;
import com.echovale.playlist.application.dto.PlaylistDTO;
import com.echovale.playlist.domain.aggregate.Playlist;
import com.echovale.playlist.domain.gateway.PlaylistApiGateway;
import com.echovale.playlist.domain.repository.PlaylistRepository;
import com.echovale.playlist.domain.service.PlaylistSupplyService;
import com.echovale.playlist.infrastructure.converter.PlaylistConverter;
import com.netease.music.api.autoconfigure.configuration.pojo.result.PlaylistResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/3 14:08
 */


@Slf4j
@Service
public class PlaylistSupplyServiceImpl implements PlaylistSupplyService {

    @Autowired
    private PlaylistApiGateway playlistApiGateway;

    @Autowired
    private PlaylistConverter playlistConverter;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private MusicConverter musicConverter;


    @Override
    public PlaylistDTO getPlaylist(NeteaseId neteaseId) {

        PlaylistResult playlistResult = playlistApiGateway.getPlaylist(neteaseId);

        List<Music> musics = playlistResult.getTracks().stream()
                .map(musicConverter::byDetailResult)
                .toList();

        Playlist playlist = playlistConverter.byResult(playlistResult);

        // 持久化
        playlist = playlistRepository.save(playlist);

        return playlistConverter.byAggregates(playlist, musics);
    }


}
