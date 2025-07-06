package com.echovale.service.mapping;

import com.echovale.domain.po.AlbumPO;
import com.echovale.domain.po.AuthorPO;
import com.echovale.service.config.MappingConfig;
import com.echovale.service.dto.AlbumDTO;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AlbumResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/7/6 21:06
 */

@Mapper(config = MappingConfig.class,
        componentModel = "spring",
        uses = {
            AlbumPOMapping.class,
            AuthorPOMapping.class})
public abstract class AlbumDTOMapping {


    public AlbumDTO byResult(AlbumResult res) {
        return coreResult(res, AlbumDTO.builder().build());
    }

    public AlbumDTO byResult(AlbumResult res, AlbumDTO dto) {
        return coreResult(res, dto);
    }


    @Mapping(source = "res.artists", target = "authors")
    @Mapping(source = "res", target = "album")
    abstract AlbumDTO autoResultMapping(AlbumResult res, @MappingTarget AlbumDTO dto);


    private AlbumDTO coreResult(AlbumResult res, AlbumDTO dto) {

        return autoResultMapping(res, dto);

    }

}
