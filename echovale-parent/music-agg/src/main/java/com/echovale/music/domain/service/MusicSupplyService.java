package com.echovale.music.domain.service;


import com.echovale.music.appliaction.dto.MusicDTO;
import com.echovale.music.domain.entity.Lyric;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicDetailResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MusicSupplyService {

    MusicDTO createAndSaveMusicFromExternal(MusicId musicId, NeteaseId neteaseId);


    Lyric createAndSaveLyricFromExternal(NeteaseId neteaseId);

    List<MusicDTO> getMusics(List<MusicId> musicIds, List<NeteaseId> neteaseIds);


    List<MusicDTO> createAndSaveMusics(List<MusicDetailResult> tracks);

    List<MusicDetailResult> obtainMusicDetailResults(List<NeteaseId> nonexistentMusicNeteaseIdList);
}
