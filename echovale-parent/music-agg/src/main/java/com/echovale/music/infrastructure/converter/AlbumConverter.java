package com.echovale.music.infrastructure.converter;

import com.echovale.music.api.vo.AlbumVO;
import com.echovale.music.appliaction.dto.AlbumDTO;
import com.echovale.music.domain.aggregate.Album;
import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.infrastructure.config.MappingConfig;
import com.echovale.music.infrastructure.po.AlbumPO;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AlbumResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;
import java.util.List;

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
                com.echovale.music.domain.valueobject.NeteaseId.class,
                java.time.LocalDateTime.class,
                java.time.Instant.class,
                java.time.ZoneId.class
        }
)
public abstract class AlbumConverter {




    @Mapping(target = "id", expression = "java(new AlbumId(res.getId()))")
    @Mapping(target = "neteaseId", expression = "java(new NeteaseId(res.getNeteaseId()))")
    abstract Album autoMapping(AlbumPO res);


    public Album byAlbumResult(AlbumPO albumPO) {
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


    public Album byAlbumResult(com.netease.music.api.autoconfigure.configuration.pojo.entity.Album apiAlbum) {
        return autoMapping(apiAlbum);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "neteaseId", expression = "java(new NeteaseId(res.getId()))")
    abstract Album autoMapping(com.netease.music.api.autoconfigure.configuration.pojo.entity.Album res);



    public Album byAlbumResult(AlbumResult albumResult) {
        return autoMapping(albumResult);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "neteaseId", expression = "java(new NeteaseId(res.getId()))")
    @Mapping(target = "publishTime", expression = "java(LocalDateTime.ofInstant(Instant.ofEpochMilli(res.getPublishTime()), ZoneId.systemDefault()))")
    abstract Album autoMapping(AlbumResult res);


    public AlbumDTO toDTO(Album album, List<Author> authors) {
        return AlbumDTO.builder()
                .album(album)
                .authorList(authors)
                .build();
    }

}
