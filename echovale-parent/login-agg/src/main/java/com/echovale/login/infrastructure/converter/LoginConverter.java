package com.echovale.login.infrastructure.converter;


import com.echovale.common.domain.infrastructure.config.MappingConfig;
import com.echovale.login.api.dto.LoginRequest;
import com.echovale.login.api.vo.LoginResult;
import com.echovale.login.application.command.LoginCommand;
import com.echovale.login.domain.aggregate.User;
import org.mapstruct.Mapper;

@Mapper(
        config = MappingConfig.class,
        componentModel = "spring"
)
public interface LoginConverter {


    LoginResult byUser(User user);


    LoginCommand byRequest(LoginRequest loginRequest);
}
