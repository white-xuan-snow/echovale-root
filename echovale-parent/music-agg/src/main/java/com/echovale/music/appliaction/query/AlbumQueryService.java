package com.echovale.music.appliaction.query;


import com.echovale.music.domain.aggregate.Album;
import com.echovale.music.domain.valueobject.AlbumId;
import org.springframework.stereotype.Service;

@Service
public interface AlbumQueryService {
    Album queryAlbumById(AlbumId albumId);
}
