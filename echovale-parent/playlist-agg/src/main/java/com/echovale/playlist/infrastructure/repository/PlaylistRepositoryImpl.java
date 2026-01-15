package com.echovale.playlist.infrastructure.repository;

import com.echovale.playlist.domain.aggregate.Playlist;
import com.echovale.playlist.domain.repository.PlaylistRepository;
import com.echovale.playlist.infrastructure.converter.PlaylistConverter;
import com.echovale.playlist.infrastructure.mapper.PlaylistMapper;
import com.echovale.playlist.infrastructure.po.PlaylistPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/2 08:14
 */

@Slf4j
@Service
public class PlaylistRepositoryImpl implements PlaylistRepository {

    @Autowired
    private PlaylistMapper playlistMapper;

    @Autowired
    private PlaylistConverter playlistConverter;

    @Override
    public Playlist save(Playlist playlist) {

        PlaylistPO playlistPO = playlistConverter.byAggregate(playlist);
        playlistMapper.insertOrUpdate(playlistPO);

        return playlistConverter.byPO(playlistPO);
    }
}
