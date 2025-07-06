package com.echovale.service;


import com.echovale.domain.po.LyricPO;
import com.echovale.domain.po.MusicInfoExtendPO;
import com.echovale.service.dto.MusicDTO;
import com.echovale.domain.po.MusicPO;
import com.echovale.service.vo.MusicUrlVO;
import com.netease.music.api.autoconfigure.configuration.pojo.result.ChorusResult;
import com.netease.music.api.autoconfigure.configuration.pojo.result.LyricsResult;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicSummaryResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public interface MusicService {
    List<MusicUrlVO> elicitMusicUrl(List<Long> ids, String level) throws Exception;
    List<MusicDTO> elicitMusic(List<Long> ids) throws Exception;

    List<Long> nonentityNeteaseIds(List<Long> neteaseIds);

    List<ChorusResult> elicitChorus(List<Long> nonentityNeteaseIds) throws Exception;

    @Async("ServiceNoneCore")
    CompletableFuture<LyricsResult> elicitLyrics(Long nonentityNeteaseId) throws Exception;

    @Async("ServiceNoneCore")
    CompletableFuture<MusicSummaryResult> elicitSummary(Long nonentityNeteaseId);

    void insertLyrics(List<LyricPO> lyricPOList);

    void insertSummary(List<MusicSummaryResult> summaryDTOList);

    void insertMusics(List<MusicPO> musicPOList);

    void insertInfosExtend(List<MusicInfoExtendPO> musicInfoExtendPOList);

    List<Long> NeteaseToMusicIds(List<Long> nonentityNeteaseMusicIds);
}
