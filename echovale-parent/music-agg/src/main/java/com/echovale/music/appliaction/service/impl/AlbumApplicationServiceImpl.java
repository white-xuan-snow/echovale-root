package com.echovale.music.appliaction.service.impl;

import com.echovale.music.appliaction.query.AlbumQueryService;
import com.echovale.music.appliaction.service.AlbumApplicationService;
import com.echovale.music.domain.aggregate.Album;
import com.echovale.music.domain.repository.AlbumRepository;
import com.echovale.music.domain.valueobject.AlbumId;
import com.echovale.music.infrastructure.converter.AlbumConverter;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AlbumResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/17 14:54
 */


@Slf4j
@Service
public class AlbumApplicationServiceImpl implements AlbumApplicationService {


    @Autowired
    private AlbumConverter albumConverter;

    @Autowired
    private AlbumRepository albumRepository;


    @Autowired
    private AlbumQueryService albumQueryService;



    @Override
    public List<Album> saveAndQueryAlbumsByExternalResult(List<AlbumResult> albumResults) {
        if (albumResults.isEmpty()) {
            return List.of();
        }



        List<Album> albums = albumResults.stream()
                .map(albumConverter::byInsufficientResult)
                .toList();

        albums = albumRepository.saveAll(albums);

        return albums;
    }

    @Override
    public List<Album> fetchAlbumsByAlbumIds(List<AlbumId> albumIds) {
        if (CollectionUtils.isEmpty(albumIds)) {
            return Collections.emptyList();
        }

        List<Album> albumList = albumQueryService.queryAlbumsByAlbumIds(albumIds);


        return albumList;
    }


}
