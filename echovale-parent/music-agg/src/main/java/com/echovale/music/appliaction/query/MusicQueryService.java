package com.echovale.music.appliaction.query;

import com.echovale.music.appliaction.dto.MusicIdMapping;
import com.echovale.music.domain.aggregate.Music;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface MusicQueryService {

    List<MusicIdMapping> queryMusicDoubleKeyByIds(List<MusicId> ids);

    Boolean queryMusicExistsByNeteaseId(NeteaseId neteaseId);

    MusicId queryMusicIdByNeteaseId(NeteaseId neteaseId);

    Music queryMusicByIds(MusicId musicId, NeteaseId neteaseId);

    NeteaseId queryNeteaseIdById(MusicId id);
}
