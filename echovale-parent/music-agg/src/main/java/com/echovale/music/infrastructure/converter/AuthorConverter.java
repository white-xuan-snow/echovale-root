package com.echovale.music.infrastructure.converter;

import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.infrastructure.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/18 21:33
 */

@Mapper(config = MappingConfig.class,
        componentModel = "spring",
        imports = {
                com.echovale.music.domain.valueobject.NeteaseId.class
        }
)
public abstract class AuthorConverter {

    public Author byApi(com.netease.music.api.autoconfigure.configuration.pojo.entity.Author res) {
        return coreApi(res);
    }


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "neteaseId", expression = "java(new NeteaseId(res.getId()))")
    abstract Author toAuthorAggregate(com.netease.music.api.autoconfigure.configuration.pojo.entity.Author res);


    private Author coreApi(com.netease.music.api.autoconfigure.configuration.pojo.entity.Author res) {
        return toAuthorAggregate(res);
    }


}
