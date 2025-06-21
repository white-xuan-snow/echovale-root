package com.echovale.service.config;

import com.echovale.domain.config.DomainModuleConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/10 14:29
 */
@Configuration
@ComponentScan("com.echovale.service")
@Import({
        DomainModuleConfig.class,
})
@EnableTransactionManagement
public class ServiceModuleConfig {
}
