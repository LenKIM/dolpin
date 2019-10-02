package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.common.BaseControllerTest;
import com.great.deploy.dolpin.domain.CelebrityGroup;
import com.great.deploy.dolpin.domain.CelebrityMember;
import com.great.deploy.dolpin.domain.Pins;
import com.great.deploy.dolpin.dto.CreatePinRequest;
import com.great.deploy.dolpin.dto.DolpinRequest;
import com.great.deploy.dolpin.dto.ProofRequest;
import com.great.deploy.dolpin.repository.CelebrityGroupRepository;
import com.great.deploy.dolpin.repository.CelebrityMemberRepository;
import com.great.deploy.dolpin.repository.PinsRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DolpinControllerTest extends BaseControllerTest {

    @Autowired
    CelebrityGroupRepository celebrityGroupRepository;

    @Autowired
    CelebrityMemberRepository celebrityMemberRepository;

    @Autowired
    PinsRepository pinsRepository;

    @Test
    public void 방문인증하기() throws Exception {

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

        ProofRequest request = new ProofRequest(save.getId(), 3);
        this.mockMvc.perform(
                post("/api/report/proof")
                        .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(true))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(this.objectMapper.writeValueAsString(request))
        )
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("OKa"));
    }

    @Test
    public void 방문인증_실패하기() throws Exception {

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
        pinsRepository.save(two);

        ProofRequest request = new ProofRequest(212L, 3);
        this.mockMvc.perform(
                post("/api/report/proof")
                        .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(true))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(this.objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("401"))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("인증되지 않은 핀 정보입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("data").exists());
    }


    @Test
    public void 제보하기_성공() throws Exception {
        DolpinRequest dolpinRequest = DolpinRequest.builder()
                .address("서울시 강남구")
                .detailedAddress("강남역 8번 출구")
                .celebrityMemberId(1L)
                .startDate(LocalDate.now())
                .endDate(LocalDate.of(2019, 10, 30))
                .build();

        MockMultipartFile multipartFile = new MockMultipartFile("data", "김정규.jpg",
                "image/jpeg", "SpringAAAAasdasdFramework".getBytes());

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .multipart("/api/report/dolpin")
                        .file(multipartFile)
                        .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(true))
                        .param("model", this.objectMapper.writeValueAsString(dolpinRequest))
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE);

        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("202"))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("data").value(true))
        ;

    }

    @Test
    public void 제보하기_null값실패() throws Exception {

        DolpinRequest dolpinRequest = new DolpinRequest();

        MockMultipartFile multipartFile = new MockMultipartFile("data", "김정규.jpg",
                "image/jpeg", "TestContets".getBytes());

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .multipart("/api/report/dolpin")
                        .file(multipartFile)
                        .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(true))
                        .param("model", this.objectMapper.writeValueAsString(dolpinRequest))
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE);

        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("202"))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("data").value(false))
        ;

    }
}