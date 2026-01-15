package com.echovale.music.infrastructure.converter;

import com.echovale.music.api.vo.AlbumVO;
import com.echovale.music.appliaction.dto.AlbumDTO;
import com.echovale.music.domain.aggregate.Album;
import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.valueobject.AlbumId;
import com.echovale.music.domain.valueobject.AuthorId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.common.domain.infrastructure.config.MappingConfig;
import com.echovale.music.infrastructure.converter.qualifier.MusicQualifier;
import com.echovale.music.infrastructure.po.AlbumAuthorsPO;
import com.echovale.music.infrastructure.po.AlbumPO;
import com.echovale.shared.domain.valueobject.ActivityStatus;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AlbumResult;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/19 19:16
 */



/**
 * AlbumConverter是一个Mapper接口，用于处理不同实体类之间的转换
 * 使用了MapStruct框架进行对象映射配置
 */
@Mapper(config = MappingConfig.class,        // 指定映射配置类
        componentModel = "spring",           // 设置组件模型为Spring，自动生成Spring管理的bean
        imports = {                          // 导入需要使用的类
                AlbumId.class,
                NeteaseId.class,
                LocalDateTime.class,
                Instant.class,
                ZoneId.class,
                ActivityStatus.class
        },
        uses = {                            // 指定自定义映射器
                MusicQualifier.class
        }
)
public interface AlbumConverter {


    @Mapping(target = "id", source = "res.id", qualifiedByName = "mapAlbumId")
    @Mapping(target = "neteaseId", source = "res.neteaseId", qualifiedByName = "mapNeteaseId")
    @Mapping(target = "status", source = "res.status", qualifiedByName = "mapStatus")
    Album byPO(AlbumPO res);


    @Mapping(target = "id", source = "res.id")
    @Mapping(target = "neteaseId", source = "res.neteaseId")
    @Mapping(target = "name", source = "res.name")

    AlbumVO byAggregate(Album res, @MappingTarget AlbumVO target);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "neteaseId", source = "res.id", qualifiedByName = "mapNeteaseId")
    Album byResult(com.netease.music.api.autoconfigure.configuration.pojo.entity.Album res);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "neteaseId", source = "res.id", qualifiedByName = "mapNeteaseId")
    @Mapping(target = "authorIds", source = "authorIds")
    Album byResult(AlbumResult res, List<AuthorId> authorIds);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "neteaseId", source = "res.id", qualifiedByName = "mapNeteaseId")
    Album byResult(AlbumResult res);

    @InheritConfiguration(name = "byResult")
    @Mapping(target = "status", expression = "java(ActivityStatus.HALF_FILLED)")
    Album byInsufficientResult(AlbumResult res);

    @InheritConfiguration(name = "byResult")
    @Mapping(target = "status", expression = "java(ActivityStatus.FULL)")
    Album bySufficientResult(AlbumResult res);


    @Mapping(target = "status", source = "res.status", qualifiedByName = "mapStatus")
    AlbumPO byAggregate(Album res);


    AlbumDTO byAggregate(Album res, List<Author> authors);

    AlbumAuthorsPO toJoin(AlbumId albumId, AuthorId authorId);

    default List<AlbumAuthorsPO> toJoin(Album album) {
        if (album == null || album.getAuthorIds() == null) {
            return List.of();
        }
        return album.getAuthorIds().stream()
                .map(authorId -> toJoin(album.getId(), authorId))
                .toList();
    }

    List<Album> byPOList(List<AlbumPO> res);
}
