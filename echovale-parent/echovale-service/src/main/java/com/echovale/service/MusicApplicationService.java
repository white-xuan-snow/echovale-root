package com.echovale.service;

import com.echovale.service.dto.MusicDTO;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicDetailResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MusicApplicationService {
    List<MusicDTO> updateMusics(List<MusicDetailResult> tracks) throws Exception;

    List<MusicDTO> elicitMusicDTOList(List<Long> musicIdList) throws Exception;
}
