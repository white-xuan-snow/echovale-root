package com.echovale.music.infrastructure.converter;

import com.echovale.music.infrastructure.converter.qualifier.MusicQualifier;
import com.echovale.shared.infrastructure.config.MappingConfig;
import org.mapstruct.Mapper;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/2/11 22:41
 */

@Mapper(config = MappingConfig.class,
        componentModel = "spring",
        uses = {
                MusicQualifier.class
        }
)
public interface FetchMusicConverter {
}
