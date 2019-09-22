package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.common.BaseControllerTest;
import com.great.deploy.dolpin.common.TestDescription;
import com.great.deploy.dolpin.domain.CelebrityGroup;
import com.great.deploy.dolpin.domain.CelebrityMember;
import com.great.deploy.dolpin.domain.Comment;
import com.great.deploy.dolpin.domain.Pins;
import com.great.deploy.dolpin.dto.CreatePinRequest;
import com.great.deploy.dolpin.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class CommentControllerTest extends BaseControllerTest {

    @Autowired
    CelebrityGroupRepository celebrityGroupRepository;

    @Autowired
    CelebrityMemberRepository celebrityMemberRepository;

    @Autowired
    PinsRepository pinsRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    AccountRepository accountRepository;
    @Before
    public void setup(){
        accountRepository.deleteAll();
    }


    @Test
    @TestDescription("하나의 pin  여러 댓글 가져오기.")
    public void getAllComment() throws Exception {

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

        Comment comment01 = new Comment(save, "하", 1L, "DolKing");
        Comment comment02 = new Comment(save, "하하", 1L, "DolKing");
        Comment comment03 = new Comment(save, "하하하", 1L, "DolKing");
        Comment comment04 = new Comment(save, "하하하하", 1L, "DolKing");
        commentRepository.save(comment01);
        commentRepository.save(comment02);
        commentRepository.save(comment03);
        commentRepository.save(comment04);


        this.mockMvc.perform(get("/api/pins/" + save.getId() + "/comments")
                .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("201"))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("OK"));
    }

}