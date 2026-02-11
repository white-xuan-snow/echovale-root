package com.echovale.music.infrastructure.converter;

import com.echovale.music.appliaction.command.FetchMusicCommand;
import com.echovale.music.domain.strategy.MusicFetchKeywords;
import com.echovale.music.infrastructure.converter.qualifier.MusicQualifier;
import com.echovale.shared.infrastructure.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/2/11 22:43
 */

@Mapper(config = MappingConfig.class,
        componentModel = "spring",
        uses = {
                MusicQualifier.class
        }
)
public interface MusicFetchKeywordsConverter {
        @Mapping(source = "res.neteaseIds", target = "neteaseIds", qualifiedByName = "mapNeteaseId")
        MusicFetchKeywords byFetchMusicCommand(FetchMusicCommand res);
}
