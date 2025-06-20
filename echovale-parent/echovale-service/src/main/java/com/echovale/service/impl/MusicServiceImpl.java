package com.echovale.service.impl;

import com.echovale.domain.mapper.LyricMapper;
import com.echovale.domain.mapper.MusicMapper;
import com.echovale.domain.model.MusicModel;
import com.echovale.domain.po.*;
import com.echovale.service.MusicService;
import com.echovale.service.vo.MusicUrlVO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.netease.music.api.autoconfigure.configuration.api.MusicApi;
import com.netease.music.api.autoconfigure.configuration.pojo.dto.ChorusDTO;
import com.netease.music.api.autoconfigure.configuration.pojo.dto.LyricsDTO;
import com.netease.music.api.autoconfigure.configuration.pojo.dto.MusicSummaryDTO;
import com.netease.music.api.autoconfigure.configuration.pojo.dto.MusicUrlDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/10 14:28
 */

@Slf4j
@Service
public class MusicServiceImpl implements MusicService {


    @Autowired
    MusicApi musicApi;

    @Autowired
    MusicMapper musicMapper;
    @Autowired
    LyricMapper lyricMapper;

    @Override
    public List<MusicUrlVO> elicitMusicUrl(List<Long> ids, String level) throws Exception {

        // 从数据库中查询neteaseIds

        List<String> neteaseIds = musicMapper.selectJoinList(String.class,
                new MPJLambdaWrapper<MusicPO>()
                        .select(MusicPO::getNeteaseId)
                        .in(MusicPO::getId, ids)
        );

        System.currentTimeMillis();


        // 音乐直链需要通过api获取
        List<MusicUrlDTO> musicUrlDTOList = musicApi.getMusicV1Url(neteaseIds, level);



        // 返回结果

        return List.of();
    }

    @Override
    public List<MusicModel> elicitMusic(List<Long> ids) throws Exception {

        // 查询


        return List.of();
    }

    @Override
    public List<Long> nonentityNeteaseIds(List<Long> neteaseIds) {
        // 已存在的id
        List<Long> existIds = musicMapper.selectObjs(new MPJLambdaWrapper<>(MusicPO.class)
                .select(MusicPO::getNeteaseId)
                .in(MusicPO::getId, neteaseIds)
        );
        // 转为HashSet(线程安全)
        HashSet<Long> nonentityNeteaseIdsSet = new HashSet<>(existIds);



        // 使用filter(HashSet::contains)过滤已存在的id
        return neteaseIds.stream()
                .filter(nonentityNeteaseIdsSet::contains)
                .toList();
    }

    @Override
    public List<ChorusDTO> elicitChorus(List<Long> nonentityNeteaseIds) throws Exception {
        return musicApi.getChorus(nonentityNeteaseIds.stream().map(Object::toString).toList());
    }

    @Async("ServiceNoneCore")
    @Override
    public CompletableFuture<LyricsDTO> elicitLyrics(Long nonentityNeteaseId) throws Exception {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return musicApi.getLyrics(nonentityNeteaseId.toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


    @Async("ServiceNoneCore")
    @Override
    public CompletableFuture<MusicSummaryDTO> elicitSummary(Long nonentityNeteaseId) {
        return CompletableFuture.supplyAsync(() -> {
            try{
                return musicApi.summary(nonentityNeteaseId.toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void insertLyrics(List<LyricsDTO> lyricsDTOList) {
        for (LyricsDTO lyricsDTO : lyricsDTOList) {
            // TODO GitHub AMLL ttml歌词获取
            LyricPO lyricPO = LyricPO.builder()
                    .neteaseLrc(lyricsDTO.getLrc())
                    .neteaseTlrc(lyricsDTO.getTlyric())
                    .neteaseYrc(lyricsDTO.getYrc())
                    .neteaseRomalyc(lyricsDTO.getRomalrc())
                    .neteaseKlrc(lyricsDTO.getKlyric())
                    .musicId(lyricsDTO.getId())
                    .build();
            lyricMapper.insert(lyricPO);
        }
    }

    @Override
    public void insertSummary(List<MusicSummaryDTO> summaryDTOList) {
        for (MusicSummaryDTO summaryDTO : summaryDTOList) {

        }
    }


}
