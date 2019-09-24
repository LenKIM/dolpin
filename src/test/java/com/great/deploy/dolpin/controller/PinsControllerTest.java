package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.common.BaseControllerTest;
import com.great.deploy.dolpin.common.TestDescription;
import com.great.deploy.dolpin.domain.CelebrityGroup;
import com.great.deploy.dolpin.domain.CelebrityMember;
import com.great.deploy.dolpin.domain.Pins;
import com.great.deploy.dolpin.dto.CelebrityRequest;
import com.great.deploy.dolpin.dto.model.CelebrityType;
import com.great.deploy.dolpin.dto.CreatePinRequest;
import com.great.deploy.dolpin.dto.PinRequest;
import com.great.deploy.dolpin.repository.CelebrityGroupRepository;
import com.great.deploy.dolpin.repository.CelebrityMemberRepository;
import com.great.deploy.dolpin.repository.PinsRepository;
import com.great.deploy.dolpin.repository.VisitRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PinsControllerTest extends BaseControllerTest {

    @Autowired
    private PinsRepository pinsRepository;

    @Autowired
    private VisitRepository visitRepository;
    @Test
    @TestDescription("Get All pins")

    public void getAllPins() throws Exception {

        this.mockMvc.perform(get("/api/pins")
                .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(true))
        )
            .andExpect(MockMvcResultMatchers.jsonPath("code").value("202"))
            .andExpect(MockMvcResultMatchers.jsonPath("msg").value("Accepted")
            );
    }

    @Test
    public void createPin() throws Exception {

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

        MockMultipartFile multipartFile = new MockMultipartFile("data", "AOasdasdA.jpg",
                "image/jpeg", "SpringAAAAasdasdFramework".getBytes());

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .multipart("/api/pins/pin")
                        .file(multipartFile)
                        .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(false))
                        .param("model", this.objectMapper.writeValueAsString(build))
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE);

        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("201"))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("Created"))
                .andExpect(MockMvcResultMatchers.jsonPath("data.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("data.img_url").exists())
        ;
    }

    @Autowired
    CelebrityGroupRepository celebrityGroupRepository;

    @Autowired
    CelebrityMemberRepository celebrityMemberRepository;


    @Test
    public void getPinDetail() throws Exception {
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

        Pins one = Pins.of(build, celebrityMember, celebrityGroup, address, detailedAddress);
        Pins two = Pins.of(build, celebrityMember, celebrityGroup, address, detailedAddress);
        Pins save = pinsRepository.save(one);
        pinsRepository.save(two);


        this.mockMvc.perform(get("/api/pins/pin/" + save.getId() + "/detail")
                .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("OK"));
    }

    @Test
    public void modifyPin() throws Exception {
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

        Pins tempPin = Pins.of(build, celebrityMember, celebrityGroup, address, detailedAddress);
        Pins save = pinsRepository.save(tempPin);

        PinRequest pinRequest = PinRequest.builder()
                .title("ILOVESPRING")
                .imgProvider("AAAAAAA")
                .imgUrl("AVHSUYDSUD")
                .latitude(37.564494D)
                .longitude(126.979677D)
                .startDate(LocalDate.of(2019, 10, 5))
                .endDate(LocalDate.of(2019, 11, 5))
                .build();

        this.mockMvc
                .perform(put("/api/pins/pin/" + save.getId())
                    .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(true))
                    .content(this.objectMapper.writeValueAsString(pinRequest))
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("data.title").value("ILOVESPRING"));
    }

    @Test
    @TestDescription("get pins By memberId and then success")
    public void getCelebritiesPinsByMember() throws Exception {
        //Given

        CelebrityRequest request = CelebrityRequest.builder()
                .celebrityId(724L)
                .celebrityType(CelebrityType.MEMBER)
                .build();

        this.mockMvc.perform(
                post("/api/pins/pin/celebrity")
                        .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(false))
                        .content(this.objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("OK"))
        ;
    }

    @Test
    @TestDescription("get pins By memberId and then Fail")
    public void getCelebritiesPinsFail() throws Exception {
        //Given

        CelebrityRequest request = CelebrityRequest.builder()
                .celebrityId(23623L)
                .celebrityType(CelebrityType.MEMBER)
                .build();

        this.mockMvc.perform(
                post("/api/pins/pin/celebrity")
                        .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(false))
                        .content(this.objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("data").isArray())
        ;
    }

    @Test
    @TestDescription("get pins By groupId and then success")
    public void getCelebritiesPinsByGroup() throws Exception {
        //Given

        CelebrityRequest request = CelebrityRequest.builder()
                .celebrityId(153L)
                .celebrityType(CelebrityType.GROUP)
                .build();

        this.mockMvc.perform(
                post("/api/pins/pin/celebrity")
                        .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(false))
                        .content(this.objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("data").isArray())
        ;
    }

    @Test
    @TestDescription("get pins By groupId and then fail")
    public void getCelebritiesPinsByGroupThenFail() throws Exception {
        //Given

        CelebrityRequest request = CelebrityRequest.builder()
                .celebrityId(15323L)
                .celebrityType(CelebrityType.GROUP)
                .build();

        this.mockMvc.perform(
                post("/api/pins/pin/celebrity")
                        .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(false))
                        .content(this.objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("data").isEmpty())
        ;
    }
}