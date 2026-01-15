package com.echovale.login.api.dto;

import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/13 22:16
 */


@Value
@Builder
@AllArgsConstructor
public class GenerateCaptchaRequest {
    String type;


    public boolean isValid() {
        if (type == null || type.isEmpty()) {
            return false;
        }
        return type.equals(CaptchaTypeConstant.SLIDER);
    }

}
