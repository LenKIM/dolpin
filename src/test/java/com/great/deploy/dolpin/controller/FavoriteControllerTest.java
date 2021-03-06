package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.common.BaseControllerTest;
import com.great.deploy.dolpin.common.TestDescription;
import com.great.deploy.dolpin.domain.Favorite;
import com.great.deploy.dolpin.dto.FavoriteRequest;
import com.great.deploy.dolpin.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FavoriteControllerTest extends BaseControllerTest {

    @Autowired
    AccountRepository accountRepository;

    @Before
    public void setUp() {
        this.accountRepository.deleteAll();
    }

    @Test
    @TestDescription("get Favorite info")
    public void favoriteGet() throws Exception {

        this.mockMvc.perform(
                get("/api/favorite/user")
                        .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(true))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("200"))
                .andExpect(jsonPath("msg").value("OK"))
                .andExpect(jsonPath("data").exists());
    }

    @Test
    @TestDescription("Favorite update")
    public void favoriteUpdate() throws Exception {

        Set<Favorite> favoriteSet = new HashSet<>();
        favoriteSet.add(new Favorite(9L, 8L));
        favoriteSet.add(new Favorite(10L, 8L));
        favoriteSet.add(new Favorite(11L, 8L));
        favoriteSet.add(new Favorite(12L, 8L));
        favoriteSet.add(new Favorite(13L, 8L));
        favoriteSet.add(new Favorite(14L, 8L));

        FavoriteRequest favoriteRequest = new FavoriteRequest(favoriteSet);

        this.mockMvc.perform(
                put("/api/favorite/user")
                        .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(true))
                        .content(this.objectMapper.writeValueAsBytes(favoriteRequest))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("202"))
                .andExpect(jsonPath("msg").value("Accepted"))
                .andExpect(jsonPath("data.favorites").isArray());
    }

    @Test
    @TestDescription("Favorite update fail")
    public void favoriteUpdateFail() throws Exception {

        Set<Favorite> favoriteSet = new HashSet<>();
        favoriteSet.add(new Favorite(9123124L, 8123124L));
        favoriteSet.add(new Favorite(10L, 8L));
        favoriteSet.add(new Favorite(11L, 8L));
        favoriteSet.add(new Favorite(122123L, 8L));
        favoriteSet.add(new Favorite(13L, 8L));
        favoriteSet.add(new Favorite(14L, 8L));

        FavoriteRequest favoriteRequest = new FavoriteRequest(favoriteSet);

        this.mockMvc.perform(
                put("/api/favorite/user")
                        .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(true))
                        .content(this.objectMapper.writeValueAsBytes(favoriteRequest))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andDo(print())
                .andExpect(jsonPath("code").value("404"))
                .andExpect(jsonPath("msg").value("Not Found"))
                .andExpect(jsonPath("data").exists());
    }
}
//
//    AccountUpdateRequest updatedAccount = AccountUpdateRequest.builder()
//            .activeRegion("경기도")
//            .duckLevel("덕덕달")
//            .nickname("BTS_LOVE_S2")
//            .medal("쿄호호오오")
//            .build();
//
//        this.mockMvc.perform(put("/api/user")
//                .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(true))
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(this.objectMapper.writeValueAsString(updatedAccount)))
