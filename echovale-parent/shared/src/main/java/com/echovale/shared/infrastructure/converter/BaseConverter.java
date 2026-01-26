package com.echovale.shared.infrastructure.converter;

import com.echovale.shared.infrastructure.config.MappingConfig;
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
