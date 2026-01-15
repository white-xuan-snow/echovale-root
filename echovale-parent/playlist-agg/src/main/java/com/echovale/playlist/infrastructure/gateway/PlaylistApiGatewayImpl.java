package com.echovale.playlist.infrastructure.gateway;

import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.playlist.domain.gateway.PlaylistApiGateway;
import com.echovale.shared.external.gateway.MusicNeteaseApiGateway;
import com.netease.music.api.autoconfigure.configuration.pojo.result.PlaylistResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/3 14:14
 */

@Slf4j
@Service
public class PlaylistApiGatewayImpl implements PlaylistApiGateway {


    @Autowired
    private MusicNeteaseApiGateway musicNeteaseApiGateway;


    @Override
    public PlaylistResult getPlaylist(NeteaseId neteaseId) {
        return musicNeteaseApiGateway.getPlaylist(neteaseId.getId());
    }
}
