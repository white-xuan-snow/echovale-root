package com.echovale.music.infrastructure.converter;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/18 10:55
 */

import com.echovale.music.api.dto.MusicUrlRequest;
import com.echovale.music.appliaction.command.ElicitMusicUrlCommand;
import com.echovale.music.infrastructure.config.MappingConfig;
import org.mapstruct.Mapper;

@Mapper(config = MappingConfig.class,
        componentModel = "spring"
)
public abstract class ElicitMusicUrlCommandConverter {


    public ElicitMusicUrlCommand byMusicUrlRequest(MusicUrlRequest res) {
        return core(res);
    }



    abstract ElicitMusicUrlCommand toCommand(MusicUrlRequest res);

    private ElicitMusicUrlCommand core(MusicUrlRequest res) {
        return toCommand(res);
    }

}
