package com.echovale.service;


import com.echovale.domain.model.MusicModel;
import com.echovale.domain.po.MusicPO;
import com.echovale.service.vo.MusicUrlVO;
import com.netease.music.api.autoconfigure.configuration.pojo.dto.ChorusDTO;
import com.netease.music.api.autoconfigure.configuration.pojo.dto.LyricsDTO;
import com.netease.music.api.autoconfigure.configuration.pojo.dto.MusicSummaryDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public interface MusicService {
    List<MusicUrlVO> elicitMusicUrl(List<Long> ids, String level) throws Exception;
    List<MusicModel> elicitMusic(List<Long> ids) throws Exception;

    List<Long> nonentityNeteaseIds(List<Long> neteaseIds);

    List<ChorusDTO> elicitChorus(List<Long> nonentityNeteaseIds) throws Exception;

    @Async("ServiceNoneCore")
    CompletableFuture<LyricsDTO> elicitLyrics(Long nonentityNeteaseId) throws Exception;

    @Async("ServiceNoneCore")
    CompletableFuture<MusicSummaryDTO> elicitSummary(Long nonentityNeteaseId);

    void insertLyrics(List<LyricsDTO> lyricsDTOList);

    void insertSummary(List<MusicSummaryDTO> summaryDTOList);

    void insertMusics(List<MusicPO> musicPOList);
}
