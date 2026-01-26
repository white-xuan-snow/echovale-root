package com.echovale.playlist.infrastructure.config;

import com.echovale.music.infrastructure.config.MusicContextConfig;
import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/1 10:22
 */


@Configuration
@Import({MusicContextConfig.class})
@ComponentScan("com.echovale.playlist")
@EnableMPP
@MapperScan("com.echovale.playlist.infrastructure.mapper")
public class PlaylistContextConfig {
}
