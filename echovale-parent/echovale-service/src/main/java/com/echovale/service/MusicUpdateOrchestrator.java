package com.echovale.service;

import com.echovale.domain.model.MusicModel;
import com.netease.music.api.autoconfigure.configuration.pojo.dto.MusicDetailDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MusicUpdateOrchestrator {
    List<MusicModel> updateMusics(List<MusicDetailDTO> tracks) throws Exception;
}
