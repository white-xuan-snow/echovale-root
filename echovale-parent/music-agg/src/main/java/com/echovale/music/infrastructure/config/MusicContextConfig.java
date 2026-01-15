package com.echovale.music.infrastructure.config;

import com.echovale.common.domain.infrastructure.config.CommonDomainContextConfig;
import com.echovale.shared.config.SharedContextConfig;
import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.validation.annotation.Validated;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/15 18:35
 */


@Configuration
@Import({
        CommonDomainContextConfig.class,
        SharedContextConfig.class
})
@ComponentScan("com.echovale.music")
@EnableMPP
@MapperScan("com.echovale.music.infrastructure.mapper")
public class MusicContextConfig {
}
