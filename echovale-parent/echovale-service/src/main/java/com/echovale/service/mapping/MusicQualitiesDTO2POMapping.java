package com.echovale.service.mapping;

import com.echovale.domain.po.MusicQualitiesPO;
import com.echovale.service.config.MappingConfig;
import com.netease.music.api.autoconfigure.configuration.pojo.entity.MusicQuality;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = MappingConfig.class,
        componentModel = "spring")
public interface MusicQualitiesDTO2POMapping {

    MusicQualitiesDTO2POMapping INSTANCE = Mappers.getMapper(MusicQualitiesDTO2POMapping.class);

    MusicQualitiesPO toPO(MusicQuality musicQuality);
}
