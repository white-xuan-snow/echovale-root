package com.echovale.common.domain.infrastructure.converter;

import com.echovale.common.domain.infrastructure.config.MappingConfig;
import com.echovale.common.domain.infrastructure.presistence.PageResult;
import com.github.pagehelper.PageInfo;
import org.mapstruct.Mapper;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/9 08:27
 */

@Mapper(config = MappingConfig.class,
        componentModel = "spring"
)
public interface BaseConverter {

}
