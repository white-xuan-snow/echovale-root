package com.echovale.music.appliaction.query;

import com.echovale.music.appliaction.dto.MusicAuthorsDTO;
import com.echovale.music.appliaction.dto.MusicIdGatherDTO;
import com.echovale.music.appliaction.dto.MusicIdMapping;
import com.echovale.music.domain.aggregate.Music;
import com.echovale.music.domain.entity.Lyric;
import com.echovale.music.domain.valueobject.AuthorId;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public interface MusicQueryService {

    List<MusicIdMapping> queryMusicDoubleKeyByIds(List<MusicId> ids);

    Boolean queryMusicExistsByNeteaseId(NeteaseId neteaseId);

    MusicId queryMusicIdByNeteaseId(NeteaseId neteaseId);

    Music queryMusicByIds(MusicId musicId, NeteaseId neteaseId);

    NeteaseId queryNeteaseIdById(MusicId id);

    Lyric queryLyricsById(MusicId id);

    List<NeteaseId> queryNonexistentNeteaseIds(List<NeteaseId> neteaseIds);

    List<Music> queryMusicByMusicIds(List<MusicId> musicIds);

    MusicAuthorsDTO queryAuthorIdsByMusicIds(List<MusicId> musicIds);

    MusicIdGatherDTO queryMusicIdGatherByNeteaseIds(List<NeteaseId> neteaseIds);

    List<MusicId> queryMusicIdsByNeteaseIds(List<NeteaseId> existentNeteaseIds);
}
