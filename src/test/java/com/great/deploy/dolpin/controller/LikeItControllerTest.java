package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.AccountRole;
import com.great.deploy.dolpin.common.BaseControllerTest;
import com.great.deploy.dolpin.common.TestDescription;
import com.great.deploy.dolpin.domain.*;
import com.great.deploy.dolpin.dto.CreatePinRequest;
import com.great.deploy.dolpin.dto.LikeItRequest;
import com.great.deploy.dolpin.repository.*;
import com.great.deploy.dolpin.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class LikeItControllerTest extends BaseControllerTest {

    @Autowired
    LikeItRepository likeItRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    PinsRepository pinsRepository;

    @Autowired
    CelebrityMemberRepository celebrityMemberRepository;
    @Autowired
    CelebrityGroupRepository celebrityGroupRepository;

    @Before
    public void setup(){
        accountRepository.deleteAll();
    }

    @Test
    @TestDescription("likeit button on")
    public void setLikeIt() throws Exception {

        //Given
        CelebrityGroup celebrityGroup = celebrityGroupRepository.findById(1L).get();
        CelebrityMember celebrityMember = celebrityMemberRepository.findById(1L).get();
        String address = "강남역 8번출구";
        String detailedAddress = "계단 오른쪽";

        CreatePinRequest build = CreatePinRequest
                .builder()
                .title("ILOVEBTS")
                .groupId(1L)
                .memberId(2L)
                .startDate(LocalDate.now())
                .endDate(LocalDate.of(2019, 10, 5))
                .imgUrl("ABCASDAJSDK")
                .imgProvider("IMAGEPROVIDER")
                .latitude(37.564494D)
                .longitude(126.979677D)
                .build();

        Pins two = Pins.of(build, celebrityMember, celebrityGroup, address, detailedAddress);
        Pins save = pinsRepository.save(two);

        Comment comment01 = new Comment(save, "하", 1, "DolKing");
        Comment saveComment = commentRepository.save(comment01);

        LikeItRequest likeItRequest = new LikeItRequest(saveComment.getId());

        this.mockMvc.perform(
                post("/api/comment/likeit")
                        .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(true))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(this.objectMapper.writeValueAsString(likeItRequest))
        ).andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("data.likeIt").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("data.Nickname").value("BTS_LOVE"))
                .andExpect(MockMvcResultMatchers.jsonPath("data.likeItCount").value("1"))
        ;
    }

    @Test
    @TestDescription("likeit을 2명 이상")
    public void setLikeIttwoMore() throws Exception {

        //Given
        CelebrityGroup celebrityGroup = celebrityGroupRepository.findById(1L).get();
        CelebrityMember celebrityMember = celebrityMemberRepository.findById(1L).get();
        String address = "강남역 8번출구";
        String detailedAddress = "계단 오른쪽";

        CreatePinRequest build = CreatePinRequest
                .builder()
                .title("ILOVEBTS")
                .groupId(1L)
                .memberId(2L)
                .startDate(LocalDate.now())
                .endDate(LocalDate.of(2019, 10, 5))
                .imgUrl("ABCASDAJSDK")
                .imgProvider("IMAGEPROVIDER")
                .latitude(37.564494D)
                .longitude(126.979677D)
                .build();

        Set<AccountRole> accountRoles = Stream.of(AccountRole.ADMIN, AccountRole.USER)
                .collect(Collectors.toSet());
        String email = "user2@gmail.com";
        String password = "user2";

        Account len = Account.builder()
                .email(email)
                .password(password)
                .medal("넌최고의팬텀이야")
                .duckLevel("달인덕")
                .activeRegion("서울")
                .nickname("BTS_LOVE")
                .imageUrl("Https://aaaa.com")
                .name("김정규")
                .oauthId(email)
                .roles(accountRoles)
                .build();

        Pins two = Pins.of(build, celebrityMember, celebrityGroup, address, detailedAddress);
        Pins save = pinsRepository.save(two);

        Comment comment01 = new Comment(save, "하", 1, "DolKing");
        Comment saveComment = commentRepository.save(comment01);

        LikeItRequest likeItRequest = new LikeItRequest(saveComment.getId());
        LikeIt likeIt1 = new LikeIt(1L, saveComment, len);
        likeItRepository.save(likeIt1);

        this.mockMvc.perform(
                post("/api/comment/likeit")
                        .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(true))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(this.objectMapper.writeValueAsString(likeItRequest))
        ).andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("data.likeIt").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("data.Nickname").value("BTS_LOVE"))
                .andExpect(MockMvcResultMatchers.jsonPath("data.likeItCount").value("2"))
        ;
    }

    @Test
    @TestDescription("likeit button off")
    public void cancelLikeIt() throws Exception {

        Set<Favorite> favoriteSet = new HashSet<>();
        favoriteSet.add(new Favorite(1L, 1L));
        favoriteSet.add(new Favorite(2L, 1L));
        favoriteSet.add(new Favorite(3L, 1L));
        Set<AccountRole> accountRoles = Stream.of(AccountRole.ADMIN, AccountRole.USER)
                .collect(Collectors.toSet());
        String email = "user@gmail.com";
        String password = "user";

        Account len = Account.builder()
                .id(3)
                .email(email)
                .password(password)
                .medal("넌최고의팬텀이야")
                .duckLevel("달인덕")
                .activeRegion("서울")
                .nickname("BTS_LOVE")
                .imageUrl("Https://aaaa.com")
                .name("김정규")
                .oauthId(email)
                .favorites(favoriteSet)
                .roles(accountRoles)
                .build();

        //Given
        CelebrityGroup celebrityGroup = celebrityGroupRepository.findById(1L).get();
        CelebrityMember celebrityMember = celebrityMemberRepository.findById(1L).get();
        String address = "강남역 8번출구";
        String detailedAddress = "계단 오른쪽";

        CreatePinRequest build = CreatePinRequest
                .builder()
                .title("ILOVEBTS")
                .groupId(1L)
                .memberId(2L)
                .startDate(LocalDate.now())
                .endDate(LocalDate.of(2019, 10, 5))
                .imgUrl("ABCASDAJSDK")
                .imgProvider("IMAGEPROVIDER")
                .latitude(37.564494D)
                .longitude(126.979677D)
                .build();

        Pins two = Pins.of(build, celebrityMember, celebrityGroup, address, detailedAddress);
        Pins save = pinsRepository.save(two);
        Comment comment01 = new Comment(save, "하", 1, "DolKing");
        Comment saveComment = commentRepository.save(comment01);
        LikeIt likeIt = new LikeIt(saveComment, len);
        likeItRepository.save(likeIt);


        this.mockMvc.perform(
                delete("/api/comment/likeit")
                        .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(true))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(this.objectMapper.writeValueAsString(saveComment.getId()))
        ).andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("202"))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("Accepted"))
                .andExpect(MockMvcResultMatchers.jsonPath("data").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("data.Nickname").value("BTS_LOVE"))
                .andExpect(MockMvcResultMatchers.jsonPath("data.likeItCount").value("1"))
        ;
    }
}