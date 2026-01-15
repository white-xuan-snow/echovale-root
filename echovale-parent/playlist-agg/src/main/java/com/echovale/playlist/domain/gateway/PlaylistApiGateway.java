package com.echovale.playlist.domain.gateway;


import com.echovale.music.domain.valueobject.NeteaseId;
import com.netease.music.api.autoconfigure.configuration.pojo.result.PlaylistResult;
import org.springframework.stereotype.Service;

@Service
public interface PlaylistApiGateway {
    PlaylistResult getPlaylist(NeteaseId neteaseId);
}
