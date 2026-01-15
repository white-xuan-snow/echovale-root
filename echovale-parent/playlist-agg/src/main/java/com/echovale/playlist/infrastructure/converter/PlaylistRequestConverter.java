package com.echovale.playlist.infrastructure.converter;

import com.echovale.common.domain.infrastructure.config.MappingConfig;
import com.echovale.playlist.api.dto.PlaylistRequest;
import com.echovale.playlist.application.command.ElicitPlaylistCommand;
import org.mapstruct.Mapper;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/2 08:22
 */

@Mapper(config = MappingConfig.class,
        componentModel = "spring"
)
public interface PlaylistRequestConverter {
    ElicitPlaylistCommand byRequest(PlaylistRequest res);



}
