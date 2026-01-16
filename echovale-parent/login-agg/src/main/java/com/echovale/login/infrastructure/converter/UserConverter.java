package com.echovale.login.infrastructure.converter;


import com.echovale.common.domain.infrastructure.config.MappingConfig;
import org.mapstruct.Mapper;

@Mapper(
        config = MappingConfig.class,
        componentModel = "spring"
)
public interface UserConverter {

}
