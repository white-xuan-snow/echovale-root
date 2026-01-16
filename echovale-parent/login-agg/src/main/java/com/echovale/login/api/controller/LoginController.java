package com.echovale.login.api.controller;

import com.echovale.login.api.dto.LoginRequest;
import com.echovale.login.api.vo.LoginResult;
import com.echovale.login.application.command.LoginCommand;
import com.echovale.login.domain.entity.LoginType;
import com.echovale.login.domain.service.LoginContext;
import com.echovale.login.domain.strategy.LoginStrategy;
import com.echovale.login.infrastructure.converter.LoginConverter;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/16 17:48
 */


@RestController
@RequestMapping("/auth/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginConverter loginConverter;
    private final LoginContext loginContext;


    @GetMapping()
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest,
                                   HttpServletRequest httpServletRequest,
                                   BindingResult bindingResult) {
        loginRequest.setIpAddress(httpServletRequest.getRemoteAddr());
//        loginRequest.setDeviceId();

        LoginCommand command = loginConverter.byRequest(loginRequest);

        LoginResult result = loginContext.execute(command);

        return ResponseEntity.ok(result);
    }

}
