package com.echovale.login.infrastructure.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/30 00:03
 */

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 1. 允许携带凭证（Cookie 必须开启这个）
        config.setAllowCredentials(true);

        // 2. 允许的源：必须指定具体的地址，不能写 "*"
        // 如果是开发环境，建议从配置文件读取这个值
        config.addAllowedOrigin("http://localhost:51731");
        config.addAllowedOrigin("http://127.0.0.1:51731");

        // 3. 允许的 Header
        config.addAllowedHeader("*");

        // 4. 允许的 Method
        config.addAllowedMethod("*");

        // 5. 预检请求的有效期（秒）
        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }


}
