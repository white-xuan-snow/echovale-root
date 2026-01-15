package com.echovale.music.infrastructure.repository;

import com.echovale.music.domain.aggregate.Album;
import com.echovale.music.domain.repository.AlbumRepository;
import com.echovale.music.infrastructure.converter.AlbumConverter;
import com.echovale.music.infrastructure.mapper.AlbumAuthorsMapper;
import com.echovale.music.infrastructure.mapper.AlbumMapper;
import com.echovale.music.infrastructure.po.AlbumAuthorsPO;
import com.echovale.music.infrastructure.po.AlbumPO;
import com.echovale.music.infrastructure.query.wrapper.AlbumWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/27 14:53
 */

@Slf4j
@Service
public class AlbumRepositoryImpl implements AlbumRepository {

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private AlbumConverter albumConverter;

    @Autowired
    private AlbumAuthorsMapper albumAuthorsMapper;


    @Override
    public Album save(Album album) {

        AlbumPO albumPO = albumConverter.byAggregate(album);
        albumMapper.insertOrUpdate(albumPO);
        // TODO BUG album的authorIds被覆盖
        album = albumConverter.byPO(albumPO);

        List<AlbumAuthorsPO> albumAuthorsPOList = albumConverter.toJoin(album);
        albumAuthorsMapper.insertOrUpdate(albumAuthorsPOList);

        return album;
    }

    @Override
    public List<Album> saveAll(List<Album> albums) {

        if (albums.isEmpty()) {
            return albums;
        }

        List<AlbumPO> albumPOList = albums.stream()
                .map(albumConverter::byAggregate)
                .toList();

        albumMapper.insertOrUpdate(albumPOList);

        return albums;
    }
}
