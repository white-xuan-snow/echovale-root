package com.echovale.service.mapping;

import com.echovale.domain.po.AuthorPO;
import com.echovale.service.config.MappingConfig;
import com.netease.music.api.autoconfigure.configuration.pojo.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MappingConfig.class,
        componentModel = "spring")
public abstract class Author2POMapping {

    @Mapping(source = "author.id", target = "neteaseId")
    @Mapping(target = "id", ignore = true)
    public abstract AuthorPO toPO(Author author);
}
