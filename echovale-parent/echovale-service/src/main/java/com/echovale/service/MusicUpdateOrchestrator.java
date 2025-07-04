package com.echovale.service;

import com.echovale.service.dto.MusicDTO;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicDetailResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MusicUpdateOrchestrator {
    List<MusicDTO> updateMusics(List<MusicDetailResult> tracks) throws Exception;
}
