package com.echovale.music.appliaction.query;


import com.echovale.music.domain.aggregate.Album;
import com.echovale.music.domain.valueobject.AlbumId;
import com.echovale.music.domain.valueobject.NeteaseId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AlbumQueryService {
    Album queryAlbumById(AlbumId albumId);

    List<NeteaseId> queryNonexistentAlbumNeteaseIds(List<NeteaseId> albumNeteaseIds);

    List<Album> queryAlbumsByAlbumIds(List<AlbumId> albumIds);
}
