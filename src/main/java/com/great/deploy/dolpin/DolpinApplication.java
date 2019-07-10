package com.great.deploy.dolpin;

import com.great.deploy.dolpin.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class DolpinApplication {

    public static void main(String[] args) {
        SpringApplication.run(DolpinApplication.class, args);
    }

}
