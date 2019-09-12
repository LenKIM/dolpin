package com.great.deploy.dolpin.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.AccountRole;
import com.great.deploy.dolpin.service.AccountService;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Ignore
public class BaseControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ModelMapper modelMapper;

    @Autowired
    protected AppProperties appProperties;

    @Autowired
    AccountService accountService;

    private Account createAccount() {
        Set<AccountRole> accountRoles = Stream.of(AccountRole.ADMIN, AccountRole.ADMIN)
                .collect(Collectors.toSet());
        Account len = Account.builder()
                .email(appProperties.getUserUsername())
                .password(appProperties.getUserPassword())
                .roles(accountRoles)
                .build();
        return this.accountService.saveAccount(len);
    }


    public String getBearerToken(boolean needToCreateAccount) throws Exception {
        return "Bearer " + getAccessToken(needToCreateAccount);
    }

    private String getAccessToken(boolean needToCreateAccount) throws Exception {

        if (needToCreateAccount) {
            Set<AccountRole> accountRoles = Stream.of(AccountRole.ADMIN, AccountRole.USER)
                    .collect(Collectors.toSet());

            Account len = Account.builder()
                    .email("joenggyu0@gmail.com")
                    .password("password")
                    .medal("넌최고의팬텀이야")
                    .duckLevel("달인덕")
                    .activeRegion("서울")
                    .nickname("BTS_LOVE")
                    .imageUrl("Https://aaaa.com")
                    .name("김정규")
                    .roles(accountRoles)
                    .build();

            this.accountService.saveAccount(len);
        }

        ResultActions perform = this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                .param("username", "joenggyu0@gmail.com")
                .param("password", "password")
                .param("grant_type", "password"));

        String responseBody = perform.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        return parser.parseMap(responseBody).get("access_token").toString();
    }
}
