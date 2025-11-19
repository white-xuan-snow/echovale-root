package com.echovale.music.infrastructure.converter;

import com.echovale.music.api.vo.AlbumVO;
import com.echovale.music.domain.aggregate.Album;
import com.echovale.music.infrastructure.config.MappingConfig;
import com.echovale.music.infrastructure.po.AlbumPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/19 19:16
 */

@Mapper(config = MappingConfig.class,
        componentModel = "spring",
        imports = {
                com.echovale.music.domain.valueobject.AlbumId.class,
                com.echovale.music.domain.valueobject.NeteaseId.class
        }
)
public abstract class AlbumConverter {




    @Mapping(target = "id", expression = "java(new AlbumId(res.getId()))")
    @Mapping(target = "neteaseId", expression = "java(new NeteaseId(res.getNeteaseId()))")
    abstract Album autoMapping(AlbumPO res);


    public Album toAggregate(AlbumPO albumPO) {
        return autoMapping(albumPO);
    }



    public AlbumVO byAggregate(Album album, AlbumVO albumVO) {
        return core(album, albumVO);
    }

    public AlbumVO byAggregate(Album album) {
        return core(album, AlbumVO.builder().build());
    }

    @Mapping(target = "id", source = "res.id")
    @Mapping(target = "neteaseId", source = "res.neteaseId")
    @Mapping(target = "name", source = "res.name")

    abstract AlbumVO autoMapping(Album res, @MappingTarget AlbumVO target);


    private AlbumVO core(Album album, AlbumVO albumVO) {
        return autoMapping(album, albumVO);
    }


    public Album toAggregate(com.netease.music.api.autoconfigure.configuration.pojo.entity.Album apiAlbum) {
        return autoMapping(apiAlbum);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "neteaseId", expression = "java(new NeteaseId(res.getId()))")
    abstract Album autoMapping(com.netease.music.api.autoconfigure.configuration.pojo.entity.Album res);



}
