package com.echovale.api;

import com.echovale.admin.config.AdminModuleConfig;
import com.echovale.client.config.ClientModuleConfig;
import com.echovale.common.config.CommonModuleConfig;
import com.echovale.domain.config.DomainModuleConfig;
import com.echovale.service.config.ServiceModuleConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
@Import({
        AdminModuleConfig.class,
        ClientModuleConfig.class,
        CommonModuleConfig.class,
        ServiceModuleConfig.class,
})
public class EchovaleWebApiApplication {


    public static void main(String[] args) {

        SpringApplication.run(EchovaleWebApiApplication.class, args);
    }

}
