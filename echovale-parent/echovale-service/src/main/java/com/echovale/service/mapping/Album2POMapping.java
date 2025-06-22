package com.echovale.service.mapping;


import com.echovale.domain.po.AlbumPO;
import com.echovale.service.config.MappingConfig;
import com.netease.music.api.autoconfigure.configuration.pojo.entity.Album;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MappingConfig.class,
        componentModel = "spring"
)
public interface Album2POMapping {

    Author2POMapping INSTANCE = Mappers.getMapper(Author2POMapping.class);

    @Mapping(source = "album.id", target = "neteaseId")
    @Mapping(target = "id", ignore = true)
    AlbumPO toPO(Album album);
}
