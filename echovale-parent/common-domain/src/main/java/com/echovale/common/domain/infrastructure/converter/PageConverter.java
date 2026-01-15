package com.echovale.common.domain.infrastructure.converter;


import com.echovale.common.domain.infrastructure.config.MappingConfig;
import com.echovale.common.domain.infrastructure.presistence.PageResult;
import com.github.pagehelper.PageInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author 30531
 */
@Mapper(config = MappingConfig.class,
        componentModel = "spring"
)
public interface PageConverter {



//    @Mapping(target = "items", source = "list")
//    @Mapping(target = "total", source = "total")
//    @Mapping(target = "page", source = "pageNum")
//    @Mapping(target = "size", source = "pageSize")
//    <T, R> PageResult<R> byInfo(PageInfo<T> res);

}
