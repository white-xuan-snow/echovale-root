package com.echovale.music.infrastructure.converter;


import com.echovale.music.api.vo.LyricVO;
import com.echovale.music.domain.entity.Lyric;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.common.domain.infrastructure.config.MappingConfig;
import com.echovale.music.infrastructure.converter.qualifier.MusicQualifier;
import com.echovale.music.infrastructure.po.LyricPO;
import com.netease.music.api.autoconfigure.configuration.pojo.result.LyricsResult;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = MappingConfig.class,
        componentModel = "spring",
        uses = {
                MusicQualifier.class
})
public interface MusicLyricConverter {


    LyricVO toVO(Lyric res, @Context List<String> types);

    @Mapping(target = "musicId", source = "res.musicId")

    Lyric toAggregate(LyricPO res);


    @Mapping(target = "musicId", source = "musicId")
    @Mapping(target = "neteaseLrc", source = "res.lrc")
    @Mapping(target = "neteaseTlrc", source = "res.tlyric")
    @Mapping(target = "neteaseYrc", source = "res.yrc")
    @Mapping(target = "neteaseKlrc", source = "res.klyric")
    @Mapping(target = "neteaseRomalrc", source = "res.romalrc")
    Lyric toLyric(LyricsResult res, MusicId musicId);

    @Mapping(target = "musicId", source = "res.musicId")

    LyricPO toPO(Lyric res);
}
