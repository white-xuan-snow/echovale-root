package com.echovale.music.infrastructure.converter;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/18 10:55
 */

import com.echovale.music.api.dto.MusicUrlRequest;
import com.echovale.music.appliaction.command.ElicitMusicUrlCommand;
import com.echovale.shared.infrastructure.config.MappingConfig;
import com.echovale.music.infrastructure.converter.qualifier.MusicQualifier;
import org.mapstruct.Mapper;

@Mapper(config = MappingConfig.class,
        componentModel = "spring",
        uses = {
                MusicQualifier.class
        }
)
public interface ElicitMusicUrlCommandConverter {
    ElicitMusicUrlCommand byRequest(MusicUrlRequest res);

}
