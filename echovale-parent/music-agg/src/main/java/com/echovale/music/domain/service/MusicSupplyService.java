package com.echovale.music.domain.service;


import com.echovale.music.appliaction.dto.MusicDTO;
import com.echovale.music.domain.aggregate.Music;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import org.springframework.stereotype.Service;

@Service
public interface MusicSupplyService {

    MusicDTO getMusic(MusicId musicId, NeteaseId neteaseId) throws Exception;


}
