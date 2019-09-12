package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.common.AppProperties;
import com.great.deploy.dolpin.common.BaseControllerTest;
import com.great.deploy.dolpin.common.TestDescription;
import com.great.deploy.dolpin.dto.AccountRequest;
import com.great.deploy.dolpin.repository.AccountRepository;
import com.great.deploy.dolpin.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest extends BaseControllerTest {


    @Autowired
    AppProperties appProperties;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Before
    public void setUp(){
        this.accountRepository.deleteAll();
    }

    @Test
    @TestDescription("계정 정보를 조회하는 테스트")
    public void existedAccountTest() throws Exception {
        this.mockMvc.perform(get("/api/user")
                .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(false))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("200"))
                .andExpect(jsonPath("msg").value("OK"))
                .andExpect(jsonPath("data.id").exists())
                .andExpect(jsonPath("data.name").value("김정규"))
                .andExpect(jsonPath("data.email").value("joenggyu0@gmail.com"));
    }

    @Test
    @TestDescription("계정 정보를 수정하는 테스트")
    public void updateAccountTest() throws Exception {

        AccountRequest updatedAccount = AccountRequest.builder()
                .activeRegion("경기도")
                .duckLevel("덕덕달")
                .nickname("BTS_LOVE_S2")
                .medal("쿄호호오오")
                .build();

        this.mockMvc.perform(put("/api/user")
                .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(false))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(updatedAccount)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("200"))
                .andExpect(jsonPath("msg").value("OK"))
                .andExpect(jsonPath("data.id").exists())
                .andExpect(jsonPath("data.name").value("김정규"))
                .andExpect(jsonPath("data.email").value("joenggyu0@gmail.com"))
                .andExpect(jsonPath("data.active_region").value("경기도"))
                .andExpect(jsonPath("data.duck_level").value("덕덕달"))
                .andExpect(jsonPath("data.nickname").value("BTS_LOVE_S2"))
        ;
    }
}