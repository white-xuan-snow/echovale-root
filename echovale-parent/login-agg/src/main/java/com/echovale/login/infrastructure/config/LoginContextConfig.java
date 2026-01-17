package com.echovale.login.infrastructure.config;

import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/15 18:23
 */

@Configuration
@ComponentScan("com.echovale.login")
@EnableMPP
@MapperScan("com.echovale.login.infrastructure.mapper")
public class LoginContextConfig {
}
