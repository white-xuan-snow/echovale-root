package com.echovale.service.impl;

import com.echovale.domain.model.MusicModel;
import com.echovale.domain.po.AlbumPO;
import com.echovale.domain.po.AuthorPO;
import com.echovale.domain.po.MusicAuthorsPO;
import com.echovale.domain.po.MusicPO;
import com.echovale.service.AlbumService;
import com.echovale.service.AuthorService;
import com.echovale.service.MusicService;
import com.echovale.service.MusicUpdateOrchestrator;
import com.echovale.service.mapping.MusicModelMapping;
import com.echovale.service.mapping.MusicPOMapping;
import com.netease.music.api.autoconfigure.configuration.api.MusicApi;
import com.netease.music.api.autoconfigure.configuration.pojo.dto.*;
import com.netease.music.api.autoconfigure.configuration.pojo.entity.Author;
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
    public List<MusicModel> updateMusics(List<MusicDetailDTO> tracks) throws Exception {
        // 先过滤存在的id
        // 再插入基本信息
        // 避免后续漏掉id详细信息的更新

        /*
        音乐部分
        */

        // 提取网易云音乐id
        List<Long> neteaseMusicIds = tracks.stream()
                .map(MusicDetailDTO::getId)
                .toList();

        // 过滤已存在的歌曲id
        List<Long> nonentityNeteaseMusicIds = musicService.nonentityNeteaseIds(neteaseMusicIds);

        // 副歌api (批处理api)
        List<ChorusDTO> chorusDTOList = musicService.elicitChorus(nonentityNeteaseMusicIds);

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
//        updateMusicAsync(nonentityNeteaseMusicIds, nonentityNeteaseAuthorIds, nonentityNeteaseAlbumIds);

        // 即刻更新部分

        HashSet<Long> nonentityNeteaseMusicIdSet = new HashSet<>(nonentityNeteaseMusicIds);

        List<MusicDetailDTO> nonentityTracks = tracks.stream()
                .map(o -> nonentityNeteaseMusicIdSet.contains(o.getId()) ? o : null)
                .toList();


        // 建立Map 方便后续匹配两个对象
        Map<Long, ChorusDTO> chorusMap = chorusDTOList.stream()
                .collect(Collectors.toMap(ChorusDTO::getId, chorusDTO -> chorusDTO));


        List<MusicModel> musicModelList = nonentityTracks.stream()
                .map(o1 -> {
                    MusicModel musicModel = musicModelMapping.detailToModel(o1);
                    musicModelMapping.chorusToModel(chorusMap.get(o1.getId()), musicModel);
                    return musicModel;
                })
                .toList();


        // 插入专辑
        List<AlbumPO> albumPOList = musicModelList.stream()
                .map(MusicModel::getAlbum)
                .toList();

        albumService.insertAlbums(albumPOList);


        for (int i = 0; i < musicModelList.size(); i++) {
            musicModelMapping.albumToModel(albumPOList.get(i), musicModelList.get(i));
        }

        // 插入音乐
        List<MusicPO> musicPOList = musicModelList.stream()
                .map(o -> musicPOMapping.modelToPO(o))
                .toList();

        musicService.insertMusics(musicPOList);

        // 将id更新到model中
        for (int i = 0; i < musicModelList.size(); i++) {
            musicModelMapping.poToModel(musicPOList.get(i), musicModelList.get(i));
        }


        // 作者
        List<AuthorPO> authorPOList = musicModelList.stream()
                .flatMap(o1 -> o1.getAuthors().stream())
                .toList();

        authorService.insertAuthors(authorPOList);

        List<MusicAuthorsPO> musicAuthorsPOList = musicModelList.stream()
                .flatMap(o1 -> o1.getAuthors().stream()
                        .map(o2 -> MusicAuthorsPO.builder()
                                .musicId(o1.getId()) // 前面先插入music获取id再返回
                                .authorId(o2.getId())
                                .build()))
                .toList();

        authorService.insertMusicAuthors(musicAuthorsPOList);

        return musicModelList;
    }


    @Async("ServiceNoneCore")
    protected void updateMusicAsync(List<Long> nonentityNeteaseMusicIds, List<Long> nonentityNeteaseAuthorIds, List<Long> nonentityNeteaseAlbumIds) throws Exception {
        // 汇总结果

        /*
        音乐部分
        */

        // 歌词api (逐次api)
        List<CompletableFuture<LyricsDTO>> lyricsFutureList = new ArrayList<>();
        for (Long id : nonentityNeteaseMusicIds) {
            lyricsFutureList.add(musicService.elicitLyrics(id));
        }

        // summary api (逐次api)
        List<CompletableFuture<MusicSummaryDTO>> summaryFutureList = new ArrayList<>();
        for (Long id : nonentityNeteaseMusicIds) {
            summaryFutureList.add(musicService.elicitSummary(id));
        }



        /*
        作者部分
        */

        // 歌手详情 api (逐次api)
        List<CompletableFuture<AuthorDetailDTO>> authorFutureList = new ArrayList<>();
        for (Long id : nonentityNeteaseAuthorIds) {
            authorFutureList.add(authorService.elicitDetails(id));
        }

        /*
        专辑部分
        */

        // 专辑api (逐次api)
        List<CompletableFuture<AlbumListDTO>> albumFutureList = new ArrayList<>();
        for (Long id : nonentityNeteaseAlbumIds) {
            albumFutureList.add(albumService.elicitAlbum(id));
        }

        /*
        汇总
        */

        List<LyricsDTO> lyricsDTOList = new ArrayList<>();
        for (CompletableFuture<LyricsDTO> future : lyricsFutureList) {
            lyricsDTOList.add(future.join());
        }
        List<MusicSummaryDTO> summaryDTOList = new ArrayList<>();
        for (CompletableFuture<MusicSummaryDTO> future : summaryFutureList) {
            summaryDTOList.add(future.join());
        }
        List<AuthorDetailDTO> authorDetailDTOList = new ArrayList<>();
        for (CompletableFuture<AuthorDetailDTO> future : authorFutureList) {
            authorDetailDTOList.add(future.join());
        }
        List<AlbumListDTO> albumListDTOList = new ArrayList<>();
        for (CompletableFuture<AlbumListDTO> future : albumFutureList) {
            albumListDTOList.add(future.join());
        }

        /*
        更新
        */

        // 歌词
        musicService.insertLyrics(lyricsDTOList);

        // summary
        musicService.insertSummary(summaryDTOList);

    }


}
