package com.echovale.music.infrastructure.converter;


import com.echovale.music.api.dto.MusicLyricRequest;
import com.echovale.music.appliaction.command.ElicitMusicLyricCommand;
import com.echovale.common.domain.infrastructure.config.MappingConfig;
import com.echovale.music.infrastructure.converter.qualifier.MusicQualifier;
import org.mapstruct.Mapper;

@Mapper(config = MappingConfig.class,
        componentModel = "spring",
        uses = {
                MusicQualifier.class
        }
)
public interface MusicCommandConverter {



        ElicitMusicLyricCommand byRequest(MusicLyricRequest res);





}
