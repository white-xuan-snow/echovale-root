package com.echovale.login.infrastructure.properties;

import com.echovale.shared.infrastructure.properties.AbstractInitProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/30 07:51
 */

@Component
@ConfigurationProperties(prefix = "login.security")
@Data
public class SecurityFilterProperties extends AbstractInitProperties {
    private Boolean remoteIpFilter;
    public static Boolean REMOTE_IP_FILTER;
}
