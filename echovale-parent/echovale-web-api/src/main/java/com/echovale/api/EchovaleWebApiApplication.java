package com.echovale.api;

import com.echovale.domain.config.DomainModuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
@Import({
        DomainModuleConfig.class
})
public class EchovaleWebApiApplication {


    public static void main(String[] args) {

        SpringApplication.run(EchovaleWebApiApplication.class, args);
    }

}
