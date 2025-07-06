package com.echovale.service.mapping;


import com.echovale.domain.po.AlbumPO;
import com.echovale.service.config.MappingConfig;
import com.netease.music.api.autoconfigure.configuration.pojo.entity.Album;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AlbumResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class,
        componentModel = "spring"
)
public abstract class AlbumPOMapping {


    @Mapping(source = "album.id", target = "neteaseId")
    @Mapping(target = "id", ignore = true)
    public abstract AlbumPO byAlbum(Album album);
    public AlbumPO byResult(AlbumResult res) {
        return coreResult(res, AlbumPO.builder().build());
    }

    public AlbumPO byResult(AlbumResult res, AlbumPO po) {
        return coreResult(res, po);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "res.id", target = "neteaseId")
    abstract AlbumPO autoResultMapping(AlbumResult res, @MappingTarget AlbumPO po);

    private AlbumPO coreResult(AlbumResult res, AlbumPO po) {
        return autoResultMapping(res, po);
    }
}
