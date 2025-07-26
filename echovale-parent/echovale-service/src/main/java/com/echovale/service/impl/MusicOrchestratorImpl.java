package com.echovale.service.impl;

import com.echovale.domain.po.*;
import com.echovale.service.dto.AlbumDTO;
import com.echovale.service.dto.MusicDTO;
import com.echovale.service.AlbumService;
import com.echovale.service.AuthorService;
import com.echovale.service.MusicService;
import com.echovale.service.MusicOrchestrator;
import com.echovale.service.mapping.*;
import com.netease.music.api.autoconfigure.configuration.pojo.result.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class MusicOrchestratorImpl implements MusicOrchestrator {


    @Autowired
    MusicService musicService;
    @Autowired
    AlbumService albumService;
    @Autowired
    AuthorService authorService;

    @Autowired
    MusicDTOMapping musicDTOMapping;
    @Autowired
    MusicPOMapping musicPOMapping;
    @Autowired
    LyricPOMapping lyricPOMapping;
    @Autowired
    AuthorPOMapping authorPOMapping;
    @Autowired
    AlbumDTOMapping albumDTOMapping;


    @Transactional
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

        // 更新数据库中不存在的音乐
        if (!nonentityNeteaseMusicIds.isEmpty()) {
            updateNonentityMusics(tracks, nonentityNeteaseMusicIds);
        }

        // 重新查询所有歌曲
//        List<MusicDTO> res = musicService.selectMusicByNeteaseIds(neteaseMusicIds);

        // 将所有DetailResult转换为DTO
        List<MusicDTO> res = tracks.stream()
                .map(o -> musicDTOMapping.byDetailResult(o))
                .toList();


        return res;
    }



    private void updateNonentityMusics(List<MusicDetailResult> tracks, List<Long> nonentityNeteaseMusicIds) throws Exception {
        // 副歌api (批处理api)
        List<ChorusResult> chorusDTOList = musicService.elicitChorus(nonentityNeteaseMusicIds);


        // 即刻更新部分
        HashSet<Long> nonentityNeteaseMusicIdSet = new HashSet<>(nonentityNeteaseMusicIds);

        List<MusicDetailResult> nonentityTracks = tracks.stream()
                .filter(o -> nonentityNeteaseMusicIdSet.contains(o.getId()))
                .toList();


        // 建立Map 方便后续匹配两个对象
        Map<Long, ChorusResult> chorusMap = chorusDTOList.stream()
                .collect(Collectors.toMap(ChorusResult::getId, chorusDTO -> chorusDTO));


        List<MusicDTO> musicDTOList = nonentityTracks.stream()
                .map(o1 -> {
                    MusicDTO musicDTO = musicDTOMapping.byDetailResult(o1);
                    musicDTOMapping.chorusToModel(chorusMap.get(o1.getId()), musicDTO);
                    return musicDTO;
                })
                .toList();
        // 插入音乐
        List<MusicPO> musicPOList = musicDTOList.stream()
                .map(o -> musicPOMapping.byDTO(o))
                .toList();

        musicService.insertMusics(musicPOList);

        // 将id更新到model中
        for (int i = 0; i < musicDTOList.size(); i++) {
            musicDTOMapping.byPO(musicPOList.get(i), musicDTOList.get(i));
        }

        // 插入音乐信息扩展表
        List<MusicInfoExtendPO> musicInfoExtendPOList = musicDTOList.stream()
                .map(MusicDTO::getInfo)
                .toList();

        // 插入或更新扩展表
        musicService.insertInfosExtend(musicInfoExtendPOList);

        log.info("MusicOrchestrator].[updateMusics] Successfully Updated {} Music(s)", musicDTOList.size());
        log.info("MusicOrchestrator].[updateMusics] 成功添加{}首歌曲", musicDTOList.size()
        );
    }



    @Override
    public List<MusicDTO> elicitMusicDTOList(List<Long> musicIdList) throws Exception {

        List<MusicDTO> musicDTOList = musicService.selectMusic(musicIdList);


        return musicDTOList;
    }


//    @Override
//    @Transactional
//    public List<MusicDTO> updateMusics(List<MusicDetailResult> tracks) throws Exception {
//        // 先过滤存在的id
//        // 再插入基本信息
//        // 避免后续漏掉id详细信息的更新
//
//        /*
//        音乐部分
//        */
//
//        // 提取网易云音乐id
//        List<Long> neteaseMusicIds = tracks.stream()
//                .map(MusicDetailResult::getId)
//                .toList();
//
//        // 过滤已存在的歌曲id
//        List<Long> nonentityNeteaseMusicIds = musicService.nonentityNeteaseIds(neteaseMusicIds);
//
//        // 副歌api (批处理api)
//        List<ChorusResult> chorusDTOList = musicService.elicitChorus(nonentityNeteaseMusicIds);
//
//        /*
//        作者部分
//        */
//
//        // 提取author id
//        List<Long> neteaseAuthorIds = tracks.stream()
//                .flatMap(a -> a.getAr().stream()) //使用flatMap展平子列表字段
//                .map(Author::getId)
//                .distinct() // 去重
//                .toList();
//
//        // 过滤已存在的id
//        List<Long> nonentityNeteaseAuthorIds = authorService.nonentityNeteaseIds(neteaseAuthorIds);
//
//        /*
//        专辑部分
//        */
//
//        // 提取专辑id
//        List<Long> neteaseAlbumIds = tracks.stream()
//                .map(o -> o.getAl().getId())
//                .distinct() // 去重
//                .toList();
//
//        // 过滤已存在的id
//        List<Long> nonentityNeteaseAlbumIds = albumService.nonentityNeteaseIds(neteaseAlbumIds);
//
//        // 即刻更新部分
//        HashSet<Long> nonentityNeteaseMusicIdSet = new HashSet<>(nonentityNeteaseMusicIds);
//
//        List<MusicDetailResult> nonentityTracks = tracks.stream()
//                .filter(o -> nonentityNeteaseMusicIdSet.contains(o.getId()))
//                .toList();
//
//
//        // 建立Map 方便后续匹配两个对象
//        Map<Long, ChorusResult> chorusMap = chorusDTOList.stream()
//                .collect(Collectors.toMap(ChorusResult::getId, chorusDTO -> chorusDTO));
//
//
//        List<MusicDTO> musicDTOList = nonentityTracks.stream()
//                .map(o1 -> {
//                    MusicDTO musicDTO = musicDTOMapping.byDetailResult(o1);
//                    musicDTOMapping.chorusToModel(chorusMap.get(o1.getId()), musicDTO);
//                    return musicDTO;
//                })
//                .toList();
//
//
//        // 插入专辑
//        List<AlbumPO> albumPOList = musicDTOList.stream()
//                .map(MusicDTO::getAlbum)
//                .toList();
//
////        albumService.insertAlbums(albumPOList);
//
//
//        for (int i = 0; i < musicDTOList.size(); i++) {
//            musicDTOMapping.albumToModel(albumPOList.get(i), musicDTOList.get(i));
//        }
//
//        // 插入音乐
//        List<MusicPO> musicPOList = musicDTOList.stream()
//                .map(o -> musicPOMapping.byDTO(o))
//                .toList();
//
//        musicService.insertMusics(musicPOList);
//
//        // 将id更新到model中
//        for (int i = 0; i < musicDTOList.size(); i++) {
//            musicDTOMapping.byPO(musicPOList.get(i), musicDTOList.get(i));
//        }
//
//        // 插入音乐信息扩展表
//        List<MusicInfoExtendPO> musicInfoExtendPOList = musicDTOList.stream()
//                .map(MusicDTO::getInfo)
//                .toList();
//
//        // 插入或更新扩展表
//        musicService.insertInfosExtend(musicInfoExtendPOList);
//
//
//
//
//        // 作者
//        List<AuthorPO> authorPOList = musicDTOList.stream()
//                .flatMap(o1 -> o1.getAuthors().stream())
//                .toList();
//
//        authorService.insertAuthors(authorPOList);
//
//        List<MusicAuthorsPO> musicAuthorsPOList = musicDTOList.stream()
//                .flatMap(o1 -> o1.getAuthors().stream()
//                        .map(o2 -> MusicAuthorsPO.builder()
//                                .musicId(o1.getId()) // 前面先插入music获取id再返回
//                                .authorId(o2.getId())
//                                .build()))
//                .toList();
//
//        authorService.insertMusicAuthors(musicAuthorsPOList);
//
//
//
//        // 异步更新部分
////        updateMusicAsync(nonentityNeteaseMusicIds, nonentityNeteaseAuthorIds, nonentityNeteaseAlbumIds);
//        log.info("MusicOrchestrator].[updateMusics] Successfully Updated {} Music(s)", musicDTOList.size());
//        log.info("MusicOrchestrator].[updateMusics] 成功添加{}首歌曲", musicDTOList.size());
//
//
//        return musicDTOList;
//    }


    // 弃用 并发量太高 api服务器会返回null
    @Async("ServiceNoneCore")
    protected void updateMusicAsync(List<Long> nonentityNeteaseMusicIds, List<Long> nonentityNeteaseAuthorIds, List<Long> nonentityNeteaseAlbumIds) throws Exception {
        // 汇总结果

        /*
        音乐部分
        */

        // 歌词api (逐次api)
        List<CompletableFuture<LyricsResult>> lyricsFutureList = new ArrayList<>();
        for (Long id : nonentityNeteaseMusicIds) {
            log.info("[MusicOrchestrator].[updateMusicAsync] 发起lyric请求API。。。");
            Thread.sleep(1000);
            lyricsFutureList.add(musicService.elicitLyricsAsync(id));
        }

        // summary api (逐次api)
        List<CompletableFuture<MusicSummaryResult>> summaryFutureList = new ArrayList<>();
        for (Long id : nonentityNeteaseMusicIds) {
            log.info("[MusicOrchestrator].[updateMusicAsync] 发起summary请求API。。。");
            Thread.sleep(1000);
            summaryFutureList.add(musicService.elicitSummary(id));
        }

        /*
        作者部分
        */

        // 歌手详情 api (逐次api)
        List<CompletableFuture<AuthorDetailResult>> authorFutureList = new ArrayList<>();
        for (Long id : nonentityNeteaseAuthorIds) {
            log.info("[MusicOrchestrator].[updateMusicAsync] 发起author请求API。。。");
            Thread.sleep(1000);
            authorFutureList.add(authorService.elicitDetailsAsync(id));
        }

        /*
        专辑部分
        */

        // 专辑api (逐次api)
        List<CompletableFuture<AlbumListResult>> albumFutureList = new ArrayList<>();
        for (Long id : nonentityNeteaseAlbumIds) {
            log.info("[MusicOrchestrator].[updateMusicAsync] 发起album请求API。。。");
            Thread.sleep(1000);
            albumFutureList.add(albumService.elicitAlbum(id));
        }

        /*
        汇总
        */

        List<LyricsResult> lyricsResList = new ArrayList<>();
        for (CompletableFuture<LyricsResult> future : lyricsFutureList) {
            lyricsResList.add(future.join());
        }
        List<MusicSummaryResult> summaryDTOList = new ArrayList<>();
        for (CompletableFuture<MusicSummaryResult> future : summaryFutureList) {
            summaryDTOList.add(future.join());
        }
        List<AuthorDetailResult> authorDetailDTOList = new ArrayList<>();
        for (CompletableFuture<AuthorDetailResult> future : authorFutureList) {
            authorDetailDTOList.add(future.join());
        }
        List<AlbumListResult> albumListResList = new ArrayList<>();
        for (CompletableFuture<AlbumListResult> future : albumFutureList) {
            albumListResList.add(future.join());
        }

        /*
        获取music id list
        */

        List<Long> musicIds = musicService.NeteaseToMusicIds(nonentityNeteaseMusicIds);


        /*
        更新
        */

        // 歌词

        List<LyricPO> lyricPOList = new ArrayList<>();
        for (int i = 0; i < musicIds.size(); i++) {
            LyricPO po = lyricPOMapping.byResult(lyricsResList.get(i), LyricPO.builder()
                    .musicId(musicIds.get(i))
                    .build());
            lyricPOList.add(po);
        }

        musicService.insertLyrics(lyricPOList);


        // summary // TODO
//        musicService.insertSummary(summaryDTOList);

        // 更新author
        List<AuthorPO> authorPOList = authorDetailDTOList.stream()
                .map(o -> authorPOMapping.byDetailRes(o))
                .toList();

        authorService.insertAuthors(authorPOList);


        // 更新album

        List<AlbumDTO> albumDTOList = albumListResList.stream()
                .map(o -> albumDTOMapping.byResult(o.getAlbum()))
                .toList();

        List<AlbumPO> albumPOList = albumDTOList.stream()
                .map(AlbumDTO::getAlbum)
                .toList();

        albumService.insertAlbums(albumPOList);

        // 专辑作者关联表

        List<AlbumAuthorsPO> albumAuthorsPOList = new ArrayList<>();

        for (int i = 0; i < albumPOList.size(); i++) {
            Long albumId = albumPOList.get(i).getId();
            List<AlbumAuthorsPO> poList = albumDTOList.get(i).getAuthors().stream()
                    .map(o -> AlbumAuthorsPO.builder()
                            .albumId(albumId)
                            .authorId(o.getId())
                            .build())
                    .toList();
            albumAuthorsPOList.addAll(poList);
        }

        albumService.insertAlbumAuthors(albumAuthorsPOList);

    }


}
