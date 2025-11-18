package com.echovale.music.infrastructure.converter;

import com.echovale.music.api.dto.MusicIncrementRequest;
import com.echovale.music.appliaction.command.AddMusicCommand;
import com.echovale.music.infrastructure.config.MappingConfig;
import org.mapstruct.Mapper;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/18 11:52
 */
@Mapper(config = MappingConfig.class,
        componentModel = "spring"
)
public abstract class AddMusicCommandConverter {

    public AddMusicCommand byIncrementCommand(MusicIncrementRequest res) {
        return core(res);
    }

    abstract AddMusicCommand toAddMusicCommand(MusicIncrementRequest res);

    private AddMusicCommand core(MusicIncrementRequest res) {
        return toAddMusicCommand(res);
    }
}
