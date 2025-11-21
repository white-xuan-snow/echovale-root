package com.echovale.music.domain.service;

import com.echovale.music.appliaction.dto.AlbumDTO;
import com.echovale.music.domain.aggregate.Album;
import com.echovale.music.domain.valueobject.AlbumId;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import org.springframework.stereotype.Service;

@Service
public interface AlbumSupplyService {
    AlbumDTO getAlbumDTO(MusicId id, NeteaseId neteaseId, AlbumId albumId);

    Album getAlbum(MusicId id, NeteaseId neteaseId, AlbumId albumId) throws Exception;
}
