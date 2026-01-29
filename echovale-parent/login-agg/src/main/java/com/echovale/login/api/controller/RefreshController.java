package com.echovale.login.api.controller;

import com.echovale.login.api.dto.LoginRequest;
import com.echovale.login.api.vo.LoginResult;
import com.echovale.login.application.command.LoginCommand;
import com.echovale.login.application.service.LoginApplicationService;
import com.echovale.login.domain.entity.LoginType;
import com.echovale.login.infrastructure.constant.LoginPaths;
import com.echovale.shared.infrastructure.presistence.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/27 02:58
 */

@RestController
@RequestMapping(LoginPaths.REFRESH)
@RequiredArgsConstructor
public class RefreshController {

    private final LoginApplicationService loginApplicationService;

    @GetMapping()
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("Refresh-Token");
        LoginCommand loginCommand = LoginCommand.builder()
                .identifier(refreshToken)
                .credential(refreshToken)
                .ipAddress(request.getRemoteAddr())
                .loginType(LoginType.REFRESH)
                .build();
        LoginResult loginResult = loginApplicationService.checkAndLogin(loginCommand);
        return ResponseEntity.ok(Result.success(loginResult));
    }
}
