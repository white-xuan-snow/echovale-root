package com.echovale.login.api.controller;

import com.echovale.login.infrastructure.constant.LoginPaths;
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
public class RefreshController {


    @GetMapping()
    public ResponseEntity<?> refreshToken() {
        return null;
    }


}
