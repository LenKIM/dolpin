package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.AccountRole;
import com.great.deploy.dolpin.common.AppProperties;
import com.great.deploy.dolpin.common.BaseControllerTest;
import com.great.deploy.dolpin.common.TestDescription;
import com.great.deploy.dolpin.repository.AccountRepository;
import com.great.deploy.dolpin.repository.CelebrityGroupRepository;
import com.great.deploy.dolpin.repository.CelebrityMemberRepository;
import com.great.deploy.dolpin.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CelebrityControllerTest extends BaseControllerTest {

    @Autowired
    CelebrityMemberRepository celebrityMemberRepository;

    @Autowired
    CelebrityGroupRepository celebrityGroupRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AppProperties appProperties;

    @Before
    public void setUp(){
        this.accountRepository.deleteAll();
    }

    @Test
    @TestDescription("연예인 개인 정보 가져오기")
    public void getCelebrityMember() throws Exception {

        this.mockMvc.perform(get("/api/celebrities")
                .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(false, () -> {
                    Set<AccountRole> accountRoles = Stream.of(AccountRole.ADMIN, AccountRole.ADMIN)
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
                    return this.accountService.saveAccount(len);
                }))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("200"))
                .andExpect(jsonPath("msg").value("OK"))
                .andExpect(jsonPath("data[0].id").exists())
                .andExpect(jsonPath("data[0].name").exists())
                .andExpect(jsonPath("data[0].birthday").exists());
    }
}