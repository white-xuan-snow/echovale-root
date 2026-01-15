package com.echovale.login.api.controller;

import cloud.tianai.captcha.application.ImageCaptchaApplication;
import cloud.tianai.captcha.application.vo.ImageCaptchaVO;
import cloud.tianai.captcha.cache.CacheStore;
import cloud.tianai.captcha.cache.impl.LocalCacheStore;
import cloud.tianai.captcha.common.AnyMap;
import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.common.response.ApiResponse;
import cloud.tianai.captcha.generator.impl.StandardConcatImageCaptchaGenerator;
import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import com.echovale.login.api.dto.GenerateCaptchaRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/10 17:30
 */

@Slf4j
@RestController
@RequestMapping("/captcha")
@CrossOrigin
public class CaptchaController {

    @Autowired
    ImageCaptchaApplication application;


    @PostMapping("/generate")
    public ApiResponse<?> getCaptchaBackground(GenerateCaptchaRequest request) {

        if (request.isValid()) {
            ApiResponse<ImageCaptchaVO> apiResponse = application.generateCaptcha(CaptchaTypeConstant.SLIDER);
            return apiResponse;
//            return CaptchaResponse.builder()
//                    .captcha(apiResponse.getData())
//                    .msg(apiResponse.getMsg())
//                    .code(apiResponse.getCode())
//                    .build();
        }

        // TODO 其余类型错误信息响应
//        return CaptchaResponse.builder()
//                .captcha("类型错误")
//                .build();
        return ApiResponse.ofSuccess();
    }

    @PostMapping("/check")
    public ApiResponse<?> checkCaptcha(@RequestBody Data data) {

        ApiResponse<?> response = application.matching(data.getId(), data.getData());


        if (response.isSuccess()) {
            // 验证码验证成功，此处应该进行自定义业务处理， 或者返回验证token进行二次验证等。
            return ApiResponse.ofSuccess(Collections.singletonMap("validToken", data.getId()));
        }




        return response;
    }


    @lombok.Data
    public static class Data {
        // 验证码id
        private String  id;
        // 验证码数据
        private ImageCaptchaTrack data;
        // 可以加用户自定义业务参数...
    }


    @lombok.Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CaptchaResponse<T> implements Serializable {
        public static final ApiResponse<?> SUCCESS = null;
        private Integer code;
        private String msg;
        private T captcha;
    }



}
