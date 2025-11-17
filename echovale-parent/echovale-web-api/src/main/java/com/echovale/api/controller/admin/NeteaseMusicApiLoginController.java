package com.echovale.api.controller.admin;

import com.echovale.service.dto.NeteaseCookieDTO;
import com.echovale.service.dto.Result;
import com.netease.music.api.autoconfigure.configuration.api.LoginApi;
import com.netease.music.api.autoconfigure.configuration.pojo.result.QrResult;
import com.netease.music.api.autoconfigure.configuration.util.Init;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/8/14 2:11
 */

@Validated
@CrossOrigin
@RestController
@RequestMapping("/admin/netease")
public class NeteaseMusicApiLoginController {

    @Autowired
    LoginApi loginApi;
    @Autowired
    Init init;


    @GetMapping("/create")
    public Result createQrCode() throws Exception {
        QrResult qrResult = loginApi.loginQrCreate();
        return Result.success(qrResult);
    }

    @PostMapping("/check/{unikey}")
    public Result checkLoginStatus(@PathVariable String unikey) throws Exception {
        Integer code = loginApi.loginQrCheck(unikey);
        return Result.success(code);
    }


    @PostMapping("/upload")
    public Result uploadCookie(@Validated @RequestBody NeteaseCookieDTO neteaseCookieDTO) throws Exception {
        init.setLocalCookie(neteaseCookieDTO.getCookie());
        return Result.success();
    }



}
