package com.echovale.login.api.controller;

import com.echovale.login.api.dto.RefreshRequest;
import com.echovale.login.application.service.LoginApplicationService;
import com.echovale.login.infrastructure.constant.Auth;
import com.echovale.login.infrastructure.properties.JwtAuthProperties;
import com.echovale.shared.infrastructure.presistence.Result;
import com.echovale.login.api.dto.LoginRequest;
import com.echovale.login.api.vo.LoginResult;
import com.echovale.login.application.command.LoginCommand;
import com.echovale.login.infrastructure.converter.LoginConverter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
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
@RequestMapping(Auth.PREFIX)
@RequiredArgsConstructor
public class AuthController {

    private final LoginConverter loginConverter;
    private final LoginApplicationService loginApplicationService;

    @GetMapping({Auth.LOGIN_SUFFIX, Auth.REFRESH_SUFFIX})
    public ResponseEntity<?> login(LoginRequest loginRequest,
                                   HttpServletRequest request,
                                   HttpServletResponse response,
                                   @CookieValue(name = "Refresh-Token", required = false) String refreshToken) {

        LoginCommand command = loginConverter.byRequest(loginRequest, refreshToken, request.getRemoteAddr());

        LoginResult res = loginApplicationService.checkAndLogin(command, response);

        return ResponseEntity.ok(Result.success(res));
    }
}
