package com.echovale.shared.properties;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/23 21:16
 */


@Component
@ConfigurationProperties("echovale")
@Data
public class ProjectProperties {

    private String name;

    public static String NAME;


    @PostConstruct
    private void init() {
        NAME = this.name;
    }


}
