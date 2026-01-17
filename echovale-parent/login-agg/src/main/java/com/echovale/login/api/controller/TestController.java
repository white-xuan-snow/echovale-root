package com.echovale.login.api.controller;

import com.echovale.common.domain.infrastructure.presistence.Result;
import com.echovale.login.infrastructure.constant.LoginPaths;
import com.echovale.login.infrastructure.redis.RefreshTokenRedisStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 23:39
 */


@RestController
@RequestMapping(LoginPaths.Test.TEST)
@RequiredArgsConstructor
public class TestController {

    private final RefreshTokenRedisStore refreshTokenRedisStore;


    @GetMapping(LoginPaths.Test.REFRESH_TOKEN)
    public ResponseEntity<?> test() {
        refreshTokenRedisStore.set("aaaaaa", "0001");
        String s = refreshTokenRedisStore.get("0001");
        return ResponseEntity.ok(Result.success(s));
    }

}
