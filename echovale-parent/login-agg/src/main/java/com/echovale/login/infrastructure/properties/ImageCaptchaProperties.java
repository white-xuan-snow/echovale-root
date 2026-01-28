package com.echovale.login.infrastructure.properties;

import com.echovale.shared.infrastructure.properties.AbstractInitProperties;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 17:35
 */

@Component
@ConfigurationProperties(prefix = "login.security.image-captcha")
@Data
public class ImageCaptchaProperties extends AbstractInitProperties {

    private String debugSecondaryToken;
    private String secondaryTokenHeaderName;

    public static String DEBUG_SECONDARY_TOKEN;
    public static String SECONDARY_TOKEN_HEADER_NAME;

}
