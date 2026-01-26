package com.echovale.music.infrastructure.config;

import com.echovale.shared.infrastructure.config.SharedContextConfig;
import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/15 18:35
 */


@Configuration
@Import({
        SharedContextConfig.class
})
@ComponentScan("com.echovale.music")
@EnableMPP
@MapperScan("com.echovale.music.infrastructure.mapper")
public class MusicContextConfig {
}
