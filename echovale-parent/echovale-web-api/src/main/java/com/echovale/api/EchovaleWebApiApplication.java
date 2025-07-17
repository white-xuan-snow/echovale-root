package com.echovale.api;

import com.echovale.client.config.ClientModuleConfig;
import com.echovale.common.config.CommonModuleConfig;
import com.echovale.service.config.ServiceModuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@Import({
        ClientModuleConfig.class,
        CommonModuleConfig.class,
        ServiceModuleConfig.class,
})
@EnableScheduling
public class EchovaleWebApiApplication {


    public static void main(String[] args) {

        SpringApplication.run(EchovaleWebApiApplication.class, args);
    }

}
