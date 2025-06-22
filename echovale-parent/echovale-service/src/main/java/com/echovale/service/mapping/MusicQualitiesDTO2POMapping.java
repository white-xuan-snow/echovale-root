package com.echovale.service.mapping;

import com.echovale.domain.po.MusicQualitiesPO;
import com.echovale.service.config.MappingConfig;
import com.netease.music.api.autoconfigure.configuration.pojo.entity.MusicQuality;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(config = MappingConfig.class,
        componentModel = "spring")
@Component
public abstract class MusicQualitiesDTO2POMapping {

    public abstract MusicQualitiesPO toPO(MusicQuality musicQuality);
}
