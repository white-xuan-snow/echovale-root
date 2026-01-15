package com.echovale.shared.config;

import com.echovale.common.domain.infrastructure.config.CommonDomainContextConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/1 11:44
 */

@Configuration
@ComponentScan("com.echovale.shared")
@Import(CommonDomainContextConfig.class)
public class SharedContextConfig {
}
