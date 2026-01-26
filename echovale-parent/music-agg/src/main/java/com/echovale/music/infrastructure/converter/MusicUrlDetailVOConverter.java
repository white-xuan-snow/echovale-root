package com.echovale.music.infrastructure.converter;

import com.echovale.music.api.vo.MusicUrlDetailVO;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.shared.infrastructure.config.MappingConfig;
import com.echovale.music.infrastructure.converter.qualifier.MusicQualifier;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicUrlResult;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 23:20
 */
@Mapper(config = MappingConfig.class,
        componentModel = "spring",
        uses = {
            MusicQualifier.class
        }
)
public interface MusicUrlDetailVOConverter {


    @Mapping(target = "id", source = "id")
    @Mapping(target = "neteaseId", source = "res.id")
    MusicUrlDetailVO byResult(MusicUrlResult res, @Context MusicId id);

}
