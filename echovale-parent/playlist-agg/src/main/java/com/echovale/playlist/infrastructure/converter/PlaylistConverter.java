package com.echovale.playlist.infrastructure.converter;


import com.echovale.common.domain.infrastructure.config.MappingConfig;
import com.echovale.common.domain.infrastructure.converter.BaseConverter;
import com.echovale.common.domain.infrastructure.converter.PageConverter;
import com.echovale.music.domain.aggregate.Music;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.infrastructure.converter.qualifier.MusicQualifier;
import com.echovale.playlist.api.vo.PlaylistVO;
import com.echovale.playlist.application.dto.PlaylistDTO;
import com.echovale.playlist.domain.aggregate.Playlist;
import com.echovale.playlist.infrastructure.converter.qualifier.PlaylistQualifier;
import com.echovale.playlist.infrastructure.po.PlaylistMusicsPO;
import com.echovale.playlist.infrastructure.po.PlaylistPO;
import com.netease.music.api.autoconfigure.configuration.pojo.result.PlaylistResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author 30531
 */
@Mapper(config = MappingConfig.class,
        componentModel = "spring",
        uses = {
                MusicQualifier.class,
                PlaylistQualifier.class
        }
)
public interface PlaylistConverter extends BaseConverter, PageConverter {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "neteaseId", source = "res.id", qualifiedByName = "mapNeteaseId")
    Playlist byResult(PlaylistResult res);




    PlaylistPO byAggregate(Playlist res);

    @Mapping(target = "id", source = "res.id", qualifiedByName = "mapPlaylistId")
    @Mapping(target = "neteaseId", source = "res.neteaseId", qualifiedByName = "mapNeteaseId")
    Playlist byPO(PlaylistPO res);

    @Mapping(target = "playlist", source = "list")
    @Mapping(target = "musicList", source = "musics")
    PlaylistDTO byAggregates(Playlist list, List<Music> musics);

    @Mapping(target = "musics", ignore = true)
    @Mapping(target = ".", source = "res.playlist")
    PlaylistVO byDTO(PlaylistDTO res);

    @Mapping(target = ".", source = "res")
    MusicId byJoinPO(PlaylistMusicsPO res);


}
