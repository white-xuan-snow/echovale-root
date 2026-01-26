package com.echovale.music.infrastructure.converter;

import com.echovale.music.api.dto.MusicPlayRequest;
import com.echovale.music.appliaction.command.PlayMusicCommand;
import com.echovale.shared.infrastructure.config.MappingConfig;
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
public abstract class PlayMusicCommandConverter {

    public PlayMusicCommand byPlayCommand(MusicPlayRequest res) {
        return core(res);
    }

    abstract PlayMusicCommand toAddMusicCommand(MusicPlayRequest res);

    private PlayMusicCommand core(MusicPlayRequest res) {
        return toAddMusicCommand(res);
    }
}
