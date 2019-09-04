package com.great.deploy.dolpin.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.datasource")
@Getter
@Setter
public class DbConfig {

    private String url;

    private String userName;

    private String password;

}
