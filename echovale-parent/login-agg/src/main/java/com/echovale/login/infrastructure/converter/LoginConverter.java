package com.echovale.login.infrastructure.converter;


import com.echovale.login.api.dto.RefreshRequest;
import com.echovale.login.domain.entity.LoginType;
import com.echovale.login.infrastructure.converter.qualifier.LoginQualifier;
import com.echovale.shared.infrastructure.config.MappingConfig;
import com.echovale.login.api.dto.LoginRequest;
import com.echovale.login.application.command.LoginCommand;
import com.echovale.login.domain.event.LoginFailedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * @author 30531
 */
@Mapper(
        config = MappingConfig.class,
        componentModel = "spring",
        imports = {
                LoginType.class
        },
        uses = {
                LoginQualifier.class
        }
)
public interface LoginConverter {


    @Mapping(target = "ipAddress", source = "ipAddress")
    @Mapping(target = "oldRefreshToken", source = "refreshToken")
    LoginCommand byRequest(LoginRequest res, String refreshToken, String ipAddress);


    @Mapping(target = "reason", source = "msg")
    @Mapping(target = "id", source = "res.identifier")
    LoginFailedEvent byCommand(LoginCommand res, String msg);


    @Mapping(target = "loginType", expression = "java(LoginType.REFRESH)")
    @Mapping(target = "identifier", source = "refreshToken")
    @Mapping(target = "credential", source = "refreshToken")
    LoginCommand byRefreshRequest(RefreshRequest res, String refreshToken, String ipAddress);




}
