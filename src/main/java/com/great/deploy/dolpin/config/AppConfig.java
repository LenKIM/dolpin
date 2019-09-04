package com.great.deploy.dolpin.config;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.AccountRole;
import com.great.deploy.dolpin.account.AccountService;
import com.great.deploy.dolpin.common.AppProperties;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Autowired
    public DbConfig dbConfig;

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {

            @Autowired
            AccountService accountService;

            @Autowired
            AppProperties appProperties;

            @Override
            public void run(ApplicationArguments args) throws Exception {

                Set<AccountRole> adminRoles = new HashSet<>();
                adminRoles.add(AccountRole.ADMIN);
                adminRoles.add(AccountRole.USER);
                Account admin = Account.builder()
                        .email(appProperties.getAdminUsername())
                        .password(appProperties.getAdminPassword())
                        .roles(adminRoles)
                        .build();
                accountService.saveAccount(admin);

                Set<AccountRole> roles = new HashSet<>();
                roles.add(AccountRole.ADMIN);
                roles.add(AccountRole.USER);
                Set<AccountRole> userRoles = new HashSet<>();
                userRoles.add(AccountRole.USER);
                Account user = Account.builder()
                        .email(appProperties.getUserUsername())
                        .password(appProperties.getUserPassword())
                        .roles(userRoles)
                        .build();
                accountService.saveAccount(user);
            }
        };
    }
}