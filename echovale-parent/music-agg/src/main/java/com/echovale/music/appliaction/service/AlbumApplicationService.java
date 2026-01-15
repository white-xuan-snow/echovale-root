package com.echovale.music.appliaction.service;


import com.echovale.music.domain.aggregate.Album;
import com.echovale.music.domain.valueobject.AlbumId;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AlbumResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AlbumApplicationService {
    List<Album> saveAndQueryAlbumsByExternalResult(List<AlbumResult> albumResults);

    List<Album> fetchAlbumsByAlbumIds(List<AlbumId> albumIds);
}
