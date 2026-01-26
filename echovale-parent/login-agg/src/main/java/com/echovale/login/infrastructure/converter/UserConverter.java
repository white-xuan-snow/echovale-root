package com.echovale.login.infrastructure.converter;


import com.echovale.shared.infrastructure.config.MappingConfig;
import com.echovale.login.domain.aggregate.User;
import com.echovale.login.infrastructure.converter.qualifier.UserQualifier;
import com.echovale.login.infrastructure.po.UserPO;
import org.mapstruct.Mapper;

@Mapper(
        config = MappingConfig.class,
        componentModel = "spring",
        uses = {
                UserQualifier.class
        }
)
public interface UserConverter {

    User byPO(UserPO res);
}
