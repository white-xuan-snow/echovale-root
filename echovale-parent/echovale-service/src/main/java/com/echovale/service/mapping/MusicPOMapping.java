package com.echovale.service.mapping;

import com.echovale.service.dto.MusicDTO;
import com.echovale.domain.po.MusicPO;
import com.echovale.service.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/23 20:33
 */

@Component
@Mapper(config = MappingConfig.class,
        componentModel = "spring"
)
public abstract class MusicPOMapping {

    @Mapping(source = "model.album.id", target = "albumId")
    public abstract MusicPO modelToPO(MusicDTO model);

    @Mapping(source = "model.album.id", target = "albumId")
    public abstract MusicPO modelToPO(MusicDTO model, @MappingTarget MusicPO po);



}
