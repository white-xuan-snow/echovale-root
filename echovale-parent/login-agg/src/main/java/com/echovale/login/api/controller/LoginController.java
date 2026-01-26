package com.echovale.login.api.controller;

import com.echovale.login.application.service.LoginApplicationService;
import com.echovale.shared.infrastructure.presistence.Result;
import com.echovale.login.api.dto.LoginRequest;
import com.echovale.login.api.vo.LoginResult;
import com.echovale.login.application.command.LoginCommand;
import com.echovale.login.domain.service.impl.LoginContext;
import com.echovale.login.infrastructure.constant.LoginPaths;
import com.echovale.login.infrastructure.converter.LoginConverter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/16 17:48
 */


@RestController
@RequestMapping(LoginPaths.LOGIN)
@RequiredArgsConstructor
public class LoginController {

    private final LoginConverter loginConverter;
    private final LoginApplicationService loginApplicationService;


    @GetMapping()
    public ResponseEntity<?> login(LoginRequest loginRequest,
                                   HttpServletRequest httpServletRequest,
                                   BindingResult bindingResult) {
        loginRequest.setIpAddress(httpServletRequest.getRemoteAddr());
//        loginRequest.setDeviceId();

        LoginCommand command = loginConverter.byRequest(loginRequest);

        LoginResult res = loginApplicationService.checkAndLogin(command);

        return ResponseEntity.ok(Result.success(res));
    }


}
