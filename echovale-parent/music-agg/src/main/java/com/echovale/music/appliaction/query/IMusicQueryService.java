package com.echovale.music.appliaction.query;

import com.echovale.music.appliaction.query.dto.MusicIdMappingDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface IMusicQueryService {

    List<MusicIdMappingDTO> queryMusicDoubleKeyByIds(List<Long> ids);
}
