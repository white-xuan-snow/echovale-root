package com.echovale.playlist.domain.service;


import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.playlist.application.dto.PlaylistDTO;
import org.springframework.stereotype.Service;

@Service
public interface PlaylistSupplyService {
    PlaylistDTO getPlaylist(NeteaseId neteaseId);
}
