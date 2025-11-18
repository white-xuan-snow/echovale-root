package com.echovale.music.infrastructure.converter;

import com.echovale.music.api.vo.MusicUrlDetailVO;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.infrastructure.config.MappingConfig;
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
        componentModel = "spring")
public abstract class MusicUrlDetailVOConverter {

    public MusicUrlDetailVO byApiResult(MusicUrlResult res, MusicId id) {
        return core(res, id);
    }

    @Mapping(target = "id", expression = "java(id.getId())")
    @Mapping(target = "neteaseId", source = "res.id")
    abstract MusicUrlDetailVO toDetailVO(MusicUrlResult res, @Context MusicId id);


    private MusicUrlDetailVO core(MusicUrlResult res, MusicId id) {
        return toDetailVO(res, id);
    }

}
