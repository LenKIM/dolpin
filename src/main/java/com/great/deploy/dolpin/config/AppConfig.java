package com.great.deploy.dolpin.config;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.AccountRole;
import com.great.deploy.dolpin.model.Provider;
import com.great.deploy.dolpin.service.AccountService;
import com.great.deploy.dolpin.common.AppProperties;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
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

                final Set<AccountRole> adminRoles = new HashSet<>();
                adminRoles.add(AccountRole.ADMIN);
                adminRoles.add(AccountRole.USER);
                Account admin = Account.builder()
                        .name("admin")
                        .imageUrl("https://m.post.naver.com/viewer/postView.nhn?volumeNo=14295665&memberNo=33408012#")
                        .nickname("여신")
                        .activeRegion("Seoul")
                        .duckLevel("아직 초보")
                        .type(Provider.SYSTEM)
                        .medal("A+")
                        .email(appProperties.getAdminUsername())
                        .password(appProperties.getAdminPassword())
                        .roles(adminRoles)
                        .createdAt(LocalDateTime.now())
                        .updateAt(LocalDateTime.now())
                        .build();

                accountService.saveAccount(admin);

                final Set<AccountRole> userRoles = new HashSet<>();
                userRoles.add(AccountRole.ADMIN);
                Account user = Account.builder()
                        .name("admin")
                        .imageUrl("https://m.post.naver.com/viewer/postView.nhn?volumeNo=14295665&memberNo=33408012#")
                        .nickname("BTS_L.O.V.E")
                        .activeRegion("Seoul")
                        .type(Provider.SYSTEM)
                        .duckLevel("이젠나도돌핀!")
                        .medal("동메달")
                        .email(appProperties.getUserUsername())
                        .password(appProperties.getUserPassword())
                        .createdAt(LocalDateTime.now())
                        .updateAt(LocalDateTime.now())
                        .roles(userRoles)
                        .build();

                accountService.saveAccount(user);
            }
        };
    }
}
