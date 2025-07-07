package com.echovale.service.impl;

import com.echovale.domain.mapper.*;
import com.echovale.service.dto.MusicDTO;
import com.echovale.domain.po.*;
import com.echovale.service.MusicService;
import com.echovale.service.mapping.MusicDTOMapping;
import com.echovale.service.mapping.MusicPOMapping;
import com.echovale.service.vo.MusicUrlVO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.netease.music.api.autoconfigure.configuration.api.MusicApi;
import com.netease.music.api.autoconfigure.configuration.pojo.result.ChorusResult;
import com.netease.music.api.autoconfigure.configuration.pojo.result.LyricsResult;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicSummaryResult;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicUrlResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
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
    TagMapper tagMapper;
    @Autowired
    MusicMapper musicMapper;
    @Autowired
    LyricMapper lyricMapper;
    @Autowired
    StyleMapper styleMapper;
    @Autowired
    LanguageMapper languageMapper;
    @Autowired
    MusicTagsMapper musicTagsMapper;
    @Autowired
    MusicStylesMapper musicStylesMapper;
    @Autowired
    MusicAwardsMapper musicAwardsMapper;
    @Autowired
    MusicSheetsMapper musicSheetsMapper;
    @Autowired
    MusicInfoExtMapper musicInfoExtMapper;
    @Autowired
    MusicLanguagesMapper musicLanguagesMapper;
    @Autowired
    MusicQualitiesMapper musicQualitiesMapper;
    @Autowired
    MusicEntertainmentMapper musicEntertainmentMapper;

    @Autowired
    MusicPOMapping musicPOMapping;
    @Autowired
    MusicDTOMapping musicDTOMapping;


    @Override
    public List<MusicUrlVO> elicitMusicUrl(List<Long> ids, String level) throws Exception {

        // 从数据库中查询neteaseIds

        List<String> neteaseIds = musicMapper.selectJoinList(String.class,
                new MPJLambdaWrapper<MusicPO>()
                        .select(MusicPO::getNeteaseId)
                        .in(MusicPO::getId, ids)
        );

        // 音乐直链需要通过api获取
        List<MusicUrlResult> musicUrlDTOList = musicApi.getMusicV1Url(neteaseIds, level);

        // 返回结果

        return List.of();
    }

    @Override
    public List<MusicDTO> elicitMusic(List<Long> ids) throws Exception {

        // 查询


        return List.of();
    }

    @Override
    public List<Long> nonentityNeteaseIds(List<Long> neteaseIds) {
        // 已存在的id
        List<Long> existIds = musicMapper.selectObjs(new MPJLambdaWrapper<>(MusicPO.class)
                .select(MusicPO::getNeteaseId)
                .in(MusicPO::getNeteaseId, neteaseIds)
        );
        // 转为HashSet(线程安全)
        HashSet<Long> nonentityNeteaseIdsSet = new HashSet<>(existIds);

        // 使用filter(HashSet::contains)过滤已存在的id
        return neteaseIds.stream()
                .filter(o -> !nonentityNeteaseIdsSet.contains(o))
                .toList();
    }

    @Override
    public List<ChorusResult> elicitChorus(List<Long> nonentityNeteaseIds) throws Exception {
        return musicApi.getChorus(nonentityNeteaseIds.stream().map(Object::toString).toList());
    }

    @Async("ServiceNoneCore")
    @Override
    public CompletableFuture<LyricsResult> elicitLyrics(Long nonentityNeteaseId) throws Exception {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return musicApi.getLyrics(nonentityNeteaseId.toString());
                // TODO AMLL github ttml歌词
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Async("ServiceNoneCore")
    @Override
    public CompletableFuture<MusicSummaryResult> elicitSummary(Long nonentityNeteaseId) {
        return CompletableFuture.supplyAsync(() -> {
            try{
                return musicApi.summary(nonentityNeteaseId.toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void insertLyrics(List<LyricPO> lyricPOList) {
        for (LyricPO po : lyricPOList) {
            // TODO GitHub AMLL ttml歌词获取



            lyricMapper.insertOrUpdate(po);
        }
    }

    @Override
    public void insertSummary(List<MusicSummaryResult> summaryDTOList) {

        List<MusicAwardsPO> musicAwardsPOList = summaryDTOList.stream()
                        .flatMap(o1 -> o1.getAwards().stream()
                                    .map(o2 -> MusicAwardsPO.builder()
                                            .musicId(o1.getId())
                                            .content(o2)
                                            .build()
                                    )
                        )
                        .toList();

        musicAwardsMapper.insertOrUpdate(musicAwardsPOList);

        List<MusicEntertainmentPO> musicEntertainmentPOList = summaryDTOList.stream()
                        .flatMap(o1 -> o1.getEntertainment().stream()
                                .map(o2 -> MusicEntertainmentPO.builder()
                                        .musicId(o1.getId())
                                        .content(o2)
                                        .build()
                                )
                        )
                        .toList();

        musicEntertainmentMapper.insertOrUpdate(musicEntertainmentPOList);

        List<StylePO> stylePOList = summaryDTOList.stream()
                .flatMap(o1 -> o1.getStyles().stream()
                        .map(o2 -> StylePO.builder()
                                .id(Long.parseLong(o2.getTagId()))
                                .name(o2.getTagName())
                                .build()
                        )
                )
                .toList();

        styleMapper.insertOrUpdate(stylePOList);

        List<TagPO> tagPOList = summaryDTOList.stream()
                .flatMap(o1 -> o1.getTags().stream()
                        .map(o2 -> TagPO.builder()
                                .id(Long.parseLong(o2.getTagId()))
                                .name(o2.getTagName())
                                .build()
                        )
                )
                .toList();

        tagMapper.insertOrUpdate(tagPOList);

        List<MusicSheetsPO> musicSheetsPOList = summaryDTOList.stream()
                .flatMap(o1 -> o1.getSheets().stream()
                        .map(o2 -> MusicSheetsPO.builder()
                                .musicId(o1.getId())
                                .url(o2.getImageUrl())
                                .name(o2.getTitle())
                                .build()
                        )
                )
                .toList();

        musicSheetsMapper.insertOrUpdate(musicSheetsPOList);

        List<LanguagePO> languagePOList = summaryDTOList.stream()
                .flatMap(o1 -> o1.getLanguages().stream()
                        .map(o2 -> LanguagePO.builder()
                                .name(o2)
                                .build()
                        )
                )
                .toList();

        languageMapper.insertOrUpdate(languagePOList);
        // TODO 联表插入


    }

    @Override
    public void insertMusics(List<MusicPO> musicPOList) {
        musicMapper.insertOrUpdate(musicPOList);
    }

    @Override
    public void insertInfosExtend(List<MusicInfoExtendPO> musicInfoExtendPOList) {

        musicInfoExtMapper.insertOrUpdate(musicInfoExtendPOList);


    }

    @Override
    public List<Long> NeteaseToMusicIds(List<Long> nonentityNeteaseMusicIds) {
        return musicMapper.selectObjs(new MPJLambdaWrapper<MusicPO>()
                .select(MusicPO::getId)
                .in(MusicPO::getNeteaseId, nonentityNeteaseMusicIds));
    }


}
