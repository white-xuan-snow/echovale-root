package com.echovale.service.impl;

import com.echovale.domain.po.*;
import com.echovale.service.dto.MusicDTO;
import com.echovale.service.AlbumService;
import com.echovale.service.AuthorService;
import com.echovale.service.MusicService;
import com.echovale.service.MusicUpdateOrchestrator;
import com.echovale.service.mapping.MusicModelMapping;
import com.echovale.service.mapping.MusicPOMapping;
import com.netease.music.api.autoconfigure.configuration.api.MusicApi;
import com.netease.music.api.autoconfigure.configuration.pojo.entity.Author;
import com.netease.music.api.autoconfigure.configuration.pojo.result.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/20 20:16
 */
@Slf4j
@Service
public class MusicUpdateOrchestratorImpl implements MusicUpdateOrchestrator {


    @Autowired
    MusicService musicService;
    @Autowired
    AlbumService albumService;
    @Autowired
    AuthorService authorService;
    @Autowired
    MusicApi music;

    @Autowired
    MusicModelMapping musicModelMapping;
    @Autowired
    MusicPOMapping musicPOMapping;



    @Override
    public List<MusicDTO> updateMusics(List<MusicDetailResult> tracks) throws Exception {
        // 先过滤存在的id
        // 再插入基本信息
        // 避免后续漏掉id详细信息的更新

        /*
        音乐部分
        */

        // 提取网易云音乐id
        List<Long> neteaseMusicIds = tracks.stream()
                .map(MusicDetailResult::getId)
                .toList();

        // 过滤已存在的歌曲id
        List<Long> nonentityNeteaseMusicIds = musicService.nonentityNeteaseIds(neteaseMusicIds);

        // 副歌api (批处理api)
        List<ChorusResult> chorusDTOList = musicService.elicitChorus(nonentityNeteaseMusicIds);

        /*
        作者部分
        */

        // 提取author id
        List<Long> neteaseAuthorIds = tracks.stream()
                .flatMap(a -> a.getAr().stream()) //使用flatMap展平子列表字段
                .map(Author::getId)
                .distinct() // 去重
                .toList();

        // 过滤已存在的id
        List<Long> nonentityNeteaseAuthorIds = authorService.nonentityNeteaseIds(neteaseAuthorIds);

        /*
        专辑部分
        */

        // 提取专辑id
        List<Long> neteaseAlbumIds = tracks.stream()
                .map(o -> o.getAl().getId())
                .distinct() // 去重
                .toList();

        // 过滤已存在的id
        List<Long> nonentityNeteaseAlbumIds = albumService.nonentityNeteaseIds(neteaseAlbumIds);


        // 异步更新部分
        updateMusicAsync(nonentityNeteaseMusicIds, nonentityNeteaseAuthorIds, nonentityNeteaseAlbumIds);

        // 即刻更新部分

        HashSet<Long> nonentityNeteaseMusicIdSet = new HashSet<>(nonentityNeteaseMusicIds);

        List<MusicDetailResult> nonentityTracks = tracks.stream()
                .map(o -> nonentityNeteaseMusicIdSet.contains(o.getId()) ? o : null)
                .toList();


        // 建立Map 方便后续匹配两个对象
        Map<Long, ChorusResult> chorusMap = chorusDTOList.stream()
                .collect(Collectors.toMap(ChorusResult::getId, chorusDTO -> chorusDTO));


        List<MusicDTO> musicDTOList = nonentityTracks.stream()
                .map(o1 -> {
                    MusicDTO musicDTO = musicModelMapping.detailToModel(o1);
                    musicModelMapping.chorusToModel(chorusMap.get(o1.getId()), musicDTO);
                    return musicDTO;
                })
                .toList();


        // 插入专辑
        List<AlbumPO> albumPOList = musicDTOList.stream()
                .map(MusicDTO::getAlbum)
                .toList();

        albumService.insertAlbums(albumPOList);


        for (int i = 0; i < musicDTOList.size(); i++) {
            musicModelMapping.albumToModel(albumPOList.get(i), musicDTOList.get(i));
        }

        // 插入音乐
        List<MusicPO> musicPOList = musicDTOList.stream()
                .map(o -> musicPOMapping.modelToPO(o))
                .toList();

        musicService.insertMusics(musicPOList);

        // 将id更新到model中
        for (int i = 0; i < musicDTOList.size(); i++) {
            musicModelMapping.poToModel(musicPOList.get(i), musicDTOList.get(i));
        }

        // 插入音乐信息扩展表
        List<MusicInfoExtendPO> musicInfoExtendPOList = musicDTOList.stream()
                .map(MusicDTO::getInfo)
                .toList();

        // 插入或更新扩展表
        musicService.insertInfosExtend(musicInfoExtendPOList);




        // 作者
        List<AuthorPO> authorPOList = musicDTOList.stream()
                .flatMap(o1 -> o1.getAuthors().stream())
                .toList();

        authorService.insertAuthors(authorPOList);

        List<MusicAuthorsPO> musicAuthorsPOList = musicDTOList.stream()
                .flatMap(o1 -> o1.getAuthors().stream()
                        .map(o2 -> MusicAuthorsPO.builder()
                                .musicId(o1.getId()) // 前面先插入music获取id再返回
                                .authorId(o2.getId())
                                .build()))
                .toList();

        authorService.insertMusicAuthors(musicAuthorsPOList);

        return musicDTOList;
    }


    @Async("ServiceNoneCore")
    protected void updateMusicAsync(List<Long> nonentityNeteaseMusicIds, List<Long> nonentityNeteaseAuthorIds, List<Long> nonentityNeteaseAlbumIds) throws Exception {
        // 汇总结果

        /*
        音乐部分
        */

        // 歌词api (逐次api)
        List<CompletableFuture<LyricsResult>> lyricsFutureList = new ArrayList<>();
        for (Long id : nonentityNeteaseMusicIds) {
            lyricsFutureList.add(musicService.elicitLyrics(id));
        }

        // summary api (逐次api)
        List<CompletableFuture<MusicSummaryResult>> summaryFutureList = new ArrayList<>();
        for (Long id : nonentityNeteaseMusicIds) {
            summaryFutureList.add(musicService.elicitSummary(id));
        }

        /*
        作者部分
        */

        // 歌手详情 api (逐次api)
        List<CompletableFuture<AuthorDetailResult>> authorFutureList = new ArrayList<>();
        for (Long id : nonentityNeteaseAuthorIds) {
            authorFutureList.add(authorService.elicitDetails(id));
        }

        /*
        专辑部分
        */

        // 专辑api (逐次api)
        List<CompletableFuture<AlbumListResult>> albumFutureList = new ArrayList<>();
        for (Long id : nonentityNeteaseAlbumIds) {
            albumFutureList.add(albumService.elicitAlbum(id));
        }

        /*
        汇总
        */

        List<LyricsResult> lyricsDTOList = new ArrayList<>();
        for (CompletableFuture<LyricsResult> future : lyricsFutureList) {
            lyricsDTOList.add(future.join());
        }
        List<MusicSummaryResult> summaryDTOList = new ArrayList<>();
        for (CompletableFuture<MusicSummaryResult> future : summaryFutureList) {
            summaryDTOList.add(future.join());
        }
        List<AuthorDetailResult> authorDetailDTOList = new ArrayList<>();
        for (CompletableFuture<AuthorDetailResult> future : authorFutureList) {
            authorDetailDTOList.add(future.join());
        }
        List<AlbumListResult> albumListDTOList = new ArrayList<>();
        for (CompletableFuture<AlbumListResult> future : albumFutureList) {
            albumListDTOList.add(future.join());
        }

        /*
        更新
        */

        // 歌词
        musicService.insertLyrics(lyricsDTOList);

        // summary
        musicService.insertSummary(summaryDTOList);

        // 更新author


    }


}
