package com.echovale.music.infrastructure.converter;

import com.echovale.music.api.vo.AuthorVO;
import com.echovale.music.domain.aggregate.Author;
import com.echovale.common.domain.infrastructure.config.MappingConfig;
import com.echovale.music.domain.valueobject.AuthorId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.converter.qualifier.MusicQualifier;
import com.echovale.music.infrastructure.po.AuthorPO;
import com.echovale.shared.domain.valueobject.ActivityStatus;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AuthorDetailResult;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AuthorResult;
import org.mapstruct.*;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/18 21:33
 */

@Mapper(config = MappingConfig.class,
        componentModel = "spring",
        imports = {
                NeteaseId.class,
                AuthorId.class,
                ActivityStatus.class
        },
        uses = {
                MusicQualifier.class
        }
)
public interface AuthorConverter {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "neteaseId", source = "res.id", qualifiedByName = "mapNeteaseId")
    Author byDetailResult(AuthorDetailResult res);

    @InheritConfiguration(name = "byDetailResult")
    @Mapping(target = "status", expression = "java(ActivityStatus.HALF_FILLED)")
    Author byInsufficientDetailResult(AuthorDetailResult res);

    @InheritConfiguration(name = "byDetailResult")
    @Mapping(target = "status", expression = "java(ActivityStatus.FULL)")
    Author bySufficientDetailResult(AuthorDetailResult res);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "neteaseId", source = "res.id", qualifiedByName = "mapNeteaseId")
    Author byResult(AuthorResult res);


    @Mapping(target = "status", source = "res.status", qualifiedByName = "mapStatus")
    AuthorPO byAggregate(Author res);

    @Mapping(target = "neteaseId", source = "res.neteaseId", qualifiedByName = "mapNeteaseId")
    @Mapping(target = "id", source = "res.id", qualifiedByName = "mapAuthorId")
    @Mapping(target = "status", source = "res.status", qualifiedByName = "mapStatus")
    Author byPO(AuthorPO res);


    @Mapping(target = "id", source = "res.id")
    @Mapping(target = "neteaseId", source = "res.id", qualifiedByName = "mapNeteaseId")
    @Mapping(target = "name", source = "res.name")
    AuthorVO byAggregate(Author res, @MappingTarget AuthorVO target);


    List<Author> byPOList(List<AuthorPO> authorPOList);
}
