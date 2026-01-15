package com.echovale.music.domain.service.impl;

import com.echovale.music.appliaction.dto.AlbumDTO;
import com.echovale.music.appliaction.query.AlbumQueryService;
import com.echovale.music.appliaction.query.AuthorQueryService;
import com.echovale.music.appliaction.query.MusicQueryService;
import com.echovale.music.domain.aggregate.Album;
import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.gateway.MusicApiGateway;
import com.echovale.music.domain.repository.AlbumRepository;
import com.echovale.music.domain.repository.AuthorRepository;
import com.echovale.music.domain.service.AlbumSupplyService;
import com.echovale.music.domain.valueobject.AlbumId;
import com.echovale.music.domain.valueobject.AuthorId;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.converter.AlbumConverter;
import com.echovale.music.infrastructure.converter.AuthorConverter;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AlbumResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/20 14:37
 */

@Slf4j
@Service
public class AlbumSupplyServiceImpl implements AlbumSupplyService {

    @Autowired
    private AlbumQueryService albumQueryService;

    @Autowired
    private AlbumConverter albumConverter;

    @Autowired
    private AuthorQueryService authorQueryService;

    @Autowired
    private MusicQueryService musicQueryService;

    @Autowired
    private MusicApiGateway musicApiGateway;

    @Autowired
    private AuthorConverter authorConverter;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public AlbumDTO getAlbumDTO(MusicId id, NeteaseId neteaseId, AlbumId albumId) {

        // 从数据库
        if (albumId.isValid()) {

            Album album = albumQueryService.queryAlbumById(albumId);

            List<Author> authors = authorQueryService.queryAuthorsByAlbumId(album.getId());

            return albumConverter.byAggregate(album, authors);
        }

        return null;
    }

    @Override
    public Album getAlbum(MusicId id, NeteaseId neteaseId, AlbumId albumId) {
        Album album;

        if (albumId.isValid()) {
            // 从数据库
            album = albumQueryService.queryAlbumById(albumId);
        } else {
            // 从外部数据
            if (!neteaseId.isValid()) {
                // 获取neteaseId
                neteaseId  = musicQueryService.queryNeteaseIdById(id);
            }
            // 这样的Album已经是获取了全部信息
            AlbumResult albumResult = musicApiGateway.elicitAlbum(neteaseId);

//            // Author聚合根持久化
//            List<Author> authors = albumResult.getArtists().stream()
//                    .map(authorConverter::byResult)
//                    .toList();
//            authors = authorRepository.save(authors);
//            List<AuthorId> authorIds = Author.getIds(authors);
//
//            // Album聚合根持久化
            album = albumConverter.byResult(albumResult);
//            album = albumRepository.save(album);



        }

        return album;
    }

    @Override
    public List<Album> createAndSaveAlbums(List<AlbumResult> albumResults) {
        List<NeteaseId> albumNeteaseIds = NeteaseId.byAlbumResult(albumResults);

        List<NeteaseId> nonexistentAlbumNeteaseIds = albumQueryService.queryNonexistentAlbumNeteaseIds(albumNeteaseIds);
        HashSet<Long> nonexistentAlbumNeteaseLongIdSets = new HashSet<>(NeteaseId.getLongList(nonexistentAlbumNeteaseIds));

        List<Album> persistentAlbums = albumResults.stream()
                .filter(albumResult -> nonexistentAlbumNeteaseLongIdSets.contains(albumResult.getId()))
                .map(albumConverter::byInsufficientResult)
                .toList();

        List<Album> existentAlbums = albumRepository.saveAll(persistentAlbums);


        return List.of();
    }



}
