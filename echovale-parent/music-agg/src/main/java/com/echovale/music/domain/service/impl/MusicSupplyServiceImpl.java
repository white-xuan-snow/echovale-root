package com.echovale.music.domain.service.impl;

import com.echovale.common.domain.api.utils.StreamUtils;
import com.echovale.music.appliaction.dto.MusicDTO;
import com.echovale.music.appliaction.query.MusicQueryService;
import com.echovale.music.domain.aggregate.Album;
import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.aggregate.Music;
import com.echovale.music.domain.entity.Lyric;
import com.echovale.music.domain.gateway.MusicApiGateway;
import com.echovale.music.domain.repository.MusicRepository;
import com.echovale.music.domain.service.AlbumSupplyService;
import com.echovale.music.domain.service.AuthorSupplyService;
import com.echovale.music.domain.service.MusicSupplyService;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.converter.AlbumConverter;
import com.echovale.music.infrastructure.converter.AuthorConverter;
import com.echovale.music.infrastructure.converter.MusicConverter;
import com.echovale.music.infrastructure.converter.MusicLyricConverter;
import com.echovale.music.infrastructure.mapper.LyricMapper;
import com.echovale.shared.utils.ListUtil;
import com.netease.music.api.autoconfigure.configuration.pojo.result.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/20 14:15
 */

@Slf4j
@Service
public class MusicSupplyServiceImpl implements MusicSupplyService {

    @Autowired
    private AlbumSupplyService albumSupplyService;

    @Autowired
    private AuthorSupplyService authorSupplyService;


    @Autowired
    private MusicQueryService musicQueryService;

    @Autowired
    private MusicApiGateway musicApiGateway;

    @Autowired
    private MusicConverter musicConverter;

    @Autowired
    private AlbumConverter albumConverter;

    @Autowired
    private AuthorConverter authorConverter;


    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private MusicLyricConverter musicLyricConverter;

    @Autowired
    private LyricMapper lyricMapper;



    @Override
    public MusicDTO createAndSaveMusicFromExternal(MusicId musicId, NeteaseId neteaseId)  {

        Music music = musicQueryService.queryMusicByIds(musicId, neteaseId);
        Album album;
        List<Author> authorList;

        if (music.isNull()) {
            log.info("[MusicSupplyServiceImpl].[getMusic] 从外部获取Music");

            MusicDetailResult musicDetailResult = musicApiGateway.elicitMusic(neteaseId);
            ChorusResult chorusResult = musicApiGateway.elicitChorus(neteaseId);

            music = musicConverter.byDetailResult(musicDetailResult);
            music = musicConverter.addChorus(chorusResult, music);

            // 持久化
            music = musicRepository.save(music);

            album = albumConverter.byResult(musicDetailResult.getAl());
            authorList = musicDetailResult.getAr().stream()
                    .map(authorConverter::byDetailResult)
                    .toList();

        } else {
            album = albumSupplyService.getAlbum(music.getId(), music.getNeteaseId(), music.getAlbumId());
            authorList = authorSupplyService.getAuthorList(music.getId(), music.getNeteaseId(), music.getAuthorIds());
        }

        return musicConverter.toDTO(music, authorList, album);
    }

    @Override
    public List<MusicDTO> createAndSaveMusics(List<MusicDetailResult> tracks) {
        if (tracks.isEmpty()) {
            return List.of();
        }

        /*
          先分割tracks、authors为已持久化部分与未持久化部分
          已持久化部分：TODO 当前保持为输入数据，可改进为从数据库查询
          未持久化部分：整理去重后从外部分获取
          合并
          重新匹配映射
         */

        // Music NeteaseId, Author NeteaseIds
        HashMap<Long, List<Long>> musicToAuthorsNeteaseMap = new HashMap<>();

        for (MusicDetailResult track : tracks) {
            musicToAuthorsNeteaseMap.put(track.getId(), track.getAr().stream().map(AuthorDetailResult::getId).toList());
        }

        List<NeteaseId> neteaseIds = NeteaseId.byLong(tracks);

        // 通过Music NeteaseId查询未持久化的NeteaseIds
        List<NeteaseId> nonexistentNeteaseIds = musicQueryService.queryNonexistentNeteaseIds(neteaseIds);


        // 划分需要持久化的tracks
        HashSet<NeteaseId> nonexistentNeteaseIdSet = new HashSet<>(nonexistentNeteaseIds);
        List<MusicDetailResult> persistentTracks = tracks.stream()
                .filter(track -> nonexistentNeteaseIdSet.contains(NeteaseId.byLong(track)))
                .toList();

        List<MusicDetailResult> existentTracks = tracks.stream()
                .filter(track -> !nonexistentNeteaseIdSet.contains(NeteaseId.byLong(track)))
                .toList();

        // Author
        List<AuthorDetailResult> distinctAuthorDetailResults = persistentTracks.stream()
                .flatMap(track -> track.getAr().stream())
                // 去重
//                .peek(author -> log.info("[MusicSupplyServiceImpl].[createAndSaveMusics] distinctAuthorDetailResults Author 去重 ID：{}", author.getId()))
                .filter(StreamUtils.distinctByKey(AuthorDetailResult::getId))
                .toList();

        // Author 持久化
        List<Author> persistentAuthors = authorSupplyService.createAndSaveAuthors(distinctAuthorDetailResults);

        // Author 合并
        List<Author> existentAuthors = existentTracks.stream()
                .flatMap(track -> track.getAr().stream())
                .map(authorConverter::byDetailResult)
                .toList();
        List<Author> totalAuthors = ListUtil.concat(existentAuthors, persistentAuthors);

        HashMap<Long, Author> authorLongNeteaseIdMap = new HashMap<>();
        for (Author author : totalAuthors) {
            authorLongNeteaseIdMap.put(author.getNeteaseId().getId(), author);
        }


        List<Music> persistentMusics = persistentTracks.stream()
                .map(musicConverter::byInsufficientDetailResult)
                .toList();
        List<ChorusResult> chorusResults = musicApiGateway.elicitChoruses(nonexistentNeteaseIds);

        // NeteaseId, Index
        HashMap<Long, Integer> chorusListNeteaseIdMap = new HashMap<>();

        for (int i = 0; i < chorusResults.size(); i++) {
            chorusListNeteaseIdMap.put(chorusResults.get(i).getId(), i);
        }

        for (int i = 0; i < persistentMusics.size(); i++) {
            long originNeteaseId = nonexistentNeteaseIds.get(i).getId();
            int chorusIndex = chorusListNeteaseIdMap.getOrDefault(originNeteaseId, 0);
            if (chorusIndex == 0) {
                continue;
            }
            musicConverter.addChorus(chorusResults.get(chorusIndex), persistentMusics.get(i));
        }


        // 持久化
        persistentMusics = musicRepository.saveAll(persistentMusics);

        List<Music> existMusics = tracks.stream()
                .filter(track -> !nonexistentNeteaseIdSet.contains(NeteaseId.byLong(track)))
                .map(musicConverter::byDetailResult)
                .toList();
        List<Music> totalMusics = ListUtil.concat(existMusics, persistentMusics);


        List<AlbumResult> albumResults = persistentTracks.stream()
                .map(MusicDetailResult::getAl)
                .toList();
        List<Album> existentAlbumResults = existentTracks.stream()
                .map(MusicDetailResult::getAl)
                .map(albumConverter::byInsufficientResult)
                .toList();

        List<Album> albums = albumSupplyService.createAndSaveAlbums(albumResults);
        List<Album> persistentAlbums = albumSupplyService.createAndSaveAlbums(albumResults);

        List<Album> totalAlbums = ListUtil.concat(existentAlbumResults, persistentAlbums);


        HashMap<Long, Album> albumNeteaseLongIdMap = new HashMap<>();
        for (Album album : totalAlbums) {
            albumNeteaseLongIdMap.put(album.getNeteaseId().getId(), album);
        }







        List<MusicDTO> musicDTOList = new ArrayList<>();
        for (int i = 0; i < totalMusics.size(); i++) {
            List<Author> authorList = new ArrayList<>();
            List<Long> authorLongNeteaseIds = musicToAuthorsNeteaseMap.get(totalMusics.get(i).getNeteaseId().getId());
            for (Long authorNeteaseId : authorLongNeteaseIds) {
                authorList.add(authorLongNeteaseIdMap.get(authorNeteaseId));
            }
            musicDTOList.add(musicConverter.toDTO(totalMusics.get(i), authorList, albums.get(i)));
        }


        return musicDTOList;
    }

    @Override
    public List<MusicDetailResult> obtainMusicDetailResults(List<NeteaseId> nonexistentMusicNeteaseIdList) {
        if (CollectionUtils.isEmpty(nonexistentMusicNeteaseIdList)) {
            return Collections.emptyList();
        }

        return musicApiGateway.elicitMusics(nonexistentMusicNeteaseIdList);
    }

    @Override
    public Lyric createAndSaveLyricFromExternal(NeteaseId neteaseId) {

        // 获取歌词数据
        LyricsResult lyricsResult = musicApiGateway.elicitLyrics(neteaseId);
        // TODO 从github上拉取amll_ttml数据

        // 查询MusicId
        MusicId musicId = musicQueryService.queryMusicIdByNeteaseId(neteaseId);

        // 持久化歌词
        Lyric lyric = musicLyricConverter.toLyric(lyricsResult, musicId);
        lyric = musicRepository.saveLyric(lyric);

        return lyric;
    }

    @Override
    public List<MusicDTO> getMusics(List<MusicId> musicIds, List<NeteaseId> neteaseIds) {
        // 从外部获取
        if (MusicId.isListEmpty(musicIds)) {

        }



        return List.of();
    }


}
