package com.echovale.service.mapping;


import com.echovale.domain.po.AlbumPO;
import com.echovale.service.config.MappingConfig;
import com.netease.music.api.autoconfigure.configuration.pojo.entity.Album;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class,
        componentModel = "spring"
)
public abstract class Album2POMapping {


    @Mapping(source = "album.id", target = "neteaseId")
    @Mapping(target = "id", ignore = true)
    public abstract AlbumPO toPO(Album album);

}
