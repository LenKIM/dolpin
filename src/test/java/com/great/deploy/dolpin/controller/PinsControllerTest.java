package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.AccountRole;
import com.great.deploy.dolpin.common.BaseControllerTest;
import com.great.deploy.dolpin.common.TestDescription;
import com.great.deploy.dolpin.dto.CreatePinRequest;
import com.great.deploy.dolpin.repository.PinsRepository;
import com.great.deploy.dolpin.service.AccountService;
import com.great.deploy.dolpin.service.PinService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PinsControllerTest extends BaseControllerTest {

    @Autowired
    private PinsRepository pinsRepository;

    @Autowired
    private PinService pinService;

    @Autowired
    private AccountService accountService;

    @Test
    @TestDescription("Get All pins")
    public void getAllPins() throws Exception {
        this.mockMvc.perform(get("/api/pins")
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
        ).andExpect(MockMvcResultMatchers.jsonPath("AA").value("AA"));
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
                .imgUrl("IMAGE_URL")
                .imgProvider("IMAGEPROVIDER")
                .latitude(37.564494D)
                .longitude(126.979677D)
                .build();

        MockMultipartFile multipartFile = new MockMultipartFile("AOA", "AOA.jpg",
                "image/jpeg", "SpringAAAAFramework".getBytes());

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.multipart("/api/pins/pin")
                        .file(multipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, super.getBearerToken(true, () -> {
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
                        .content(this.objectMapper.writeValueAsString(build))
                ;

        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("data.title").value("ILOVEBTS"))
                .andExpect(MockMvcResultMatchers.jsonPath("data.start_date").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("data.end_date").exists())
                ;
    }

    @Test
    public void getPinDetail() {
    }

    @Test
    public void modifyPin() {
    }

    @Test
    public void deletePin() {
    }
}