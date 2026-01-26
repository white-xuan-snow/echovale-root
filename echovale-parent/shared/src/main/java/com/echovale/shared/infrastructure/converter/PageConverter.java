package com.echovale.shared.infrastructure.converter;


import com.echovale.shared.infrastructure.config.MappingConfig;
import org.mapstruct.Mapper;

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
