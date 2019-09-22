package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.AccountRole;
import com.great.deploy.dolpin.common.AppProperties;
import com.great.deploy.dolpin.common.BaseControllerTest;
import com.great.deploy.dolpin.common.TestDescription;
import com.great.deploy.dolpin.domain.Favorite;
import com.great.deploy.dolpin.dto.AccountRequest;
import com.great.deploy.dolpin.dto.AccountUpdateRequest;
import com.great.deploy.dolpin.dto.LoginRequest;
import com.great.deploy.dolpin.model.Provider;
import com.great.deploy.dolpin.repository.AccountRepository;
import com.great.deploy.dolpin.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    public void setUp() {
        this.accountRepository.deleteAll();
    }

    @Test
    @TestDescription("계정 정보를 조회하는 테스트")
    public void existedAccountTest() throws Exception {
        this.mockMvc.perform(get("/api/user")
                .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(true))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("200"))
                .andExpect(jsonPath("msg").value("OK"))
                .andExpect(jsonPath("data.id").exists())
                .andExpect(jsonPath("data.name").value("김정규"))
                .andExpect(jsonPath("data.email").value("user@gmail.com"));
    }

    @Test
    @TestDescription("계정 정보를 수정하는 테스트")
    public void updateAccountTest() throws Exception {

        AccountUpdateRequest updatedAccount = AccountUpdateRequest.builder()
                .activeRegion("경기도")
                .duckLevel("덕덕달")
                .nickname("BTS_LOVE_S2")
                .medal("쿄호호오오")
                .build();

        this.mockMvc.perform(put("/api/user")
                .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(true))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(updatedAccount)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("200"))
                .andExpect(jsonPath("msg").value("OK"))
                .andExpect(jsonPath("data.id").exists())
                .andExpect(jsonPath("data.name").value("김정규"))
                .andExpect(jsonPath("data.email").value("user@gmail.com"))
                .andExpect(jsonPath("data.active_region").value("경기도"))
                .andExpect(jsonPath("data.duck_level").value("덕덕달"))
                .andExpect(jsonPath("data.nickname").value("BTS_LOVE_S2"))
        ;
    }

    @Test
    @TestDescription("check email existed")
    public void checkEmail() throws Exception {
        Set<Favorite> favoriteSet = new HashSet<>();
        favoriteSet.add(new Favorite(1L, 1L));
        favoriteSet.add(new Favorite(2L, 1L));
        favoriteSet.add(new Favorite(3L, 1L));


        Set<AccountRole> accountRoles = Stream.of(AccountRole.ADMIN, AccountRole.USER)
                .collect(Collectors.toSet());

        String email = "user2@gmail.com";
        Account len = Account.builder()
                .email(email)
                .password("password")
                .medal("넌최고의팬텀이야")
                .duckLevel("달인덕")
                .activeRegion("서울")
                .nickname("BTS_LOVE")
                .imageUrl("Https://aaaa.com")
                .name("김정규")
                .oauthId(email)
                .snsId("123123")
                .snsType(Provider.SYSTEM)
                .favorites(favoriteSet)
                .roles(accountRoles)
                .build();

        this.accountService.saveAccount(len);
        LoginRequest request = new LoginRequest(email, Provider.SYSTEM, "123123");
        this.mockMvc.perform(
                post("/api/user/exist")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(this.objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk())
                .andExpect(jsonPath("code").value("202"))
                .andExpect(jsonPath("msg").value("Accepted"))
                .andExpect(jsonPath("data").value("true"));
    }

    @Test
    @TestDescription("singUp By email")
    public void signUpByEmail() throws Exception {

        Set<Favorite> favoriteSet = new HashSet<>();
        favoriteSet.add(new Favorite(9L, 8L));
        favoriteSet.add(new Favorite(10L, 8L));
        favoriteSet.add(new Favorite(11L, 8L));
        favoriteSet.add(new Favorite(12L, 8L));
        favoriteSet.add(new Favorite(13L, 8L));
        favoriteSet.add(new Favorite(14L, 8L));

        AccountRequest testAccount = AccountRequest.builder()
                .email("joneg@gamil.com")
                .favorites(favoriteSet)
                .nickname("신사동호라이")
                .snsType(Provider.FACEBOOK)
                .build();

        this.mockMvc.perform(
                post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(this.objectMapper.writeValueAsString(testAccount))
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("201"))
                .andExpect(jsonPath("msg").value("Created"))
                .andExpect(jsonPath("data.account_id").exists());
    }

    @Test
    @TestDescription("singIn By email")
    public void signInByEmail() throws Exception {

        Set<Favorite> favoriteSet = new HashSet<>();
        favoriteSet.add(new Favorite(1L, 1L));
        favoriteSet.add(new Favorite(2L, 1L));
        favoriteSet.add(new Favorite(3L, 1L));


        Set<AccountRole> accountRoles = Stream.of(AccountRole.ADMIN, AccountRole.USER)
                .collect(Collectors.toSet());

        String email = "user@gmail.com";
        String password = "user";
        Account len = Account.builder()
                .email(email)
                .password(password)
                .medal("넌최고의팬텀이야")
                .duckLevel("달인덕")
                .activeRegion("서울")
                .nickname("BTS_LOVE")
                .imageUrl("Https://aaaa.com")
                .name("김정규")
                .favorites(favoriteSet)
                .oauthId(email)
                .snsType(Provider.SYSTEM)
                .snsId("123123")
                .roles(accountRoles)
                .build();
        this.accountService.saveAccount(len);

        LoginRequest request = new LoginRequest(email, Provider.SYSTEM, "123123");
        this.mockMvc.perform(
                post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(this.objectMapper.writeValueAsString(request))
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("200"))
                .andExpect(jsonPath("msg").value("OK"))
                .andExpect(jsonPath("data.id").exists());
    }
}