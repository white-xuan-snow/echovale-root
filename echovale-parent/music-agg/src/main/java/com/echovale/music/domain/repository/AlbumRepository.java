package com.echovale.music.domain.repository;


import com.echovale.music.domain.aggregate.Album;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository {
    Album save(Album album);

    List<Album> saveAll(List<Album> albums);
}
