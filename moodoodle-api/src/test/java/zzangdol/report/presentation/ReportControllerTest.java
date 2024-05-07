package zzangdol.report.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import zzangdol.global.annotation.AuthenticationArgumentResolver;
import zzangdol.report.business.ReportFacade;
import zzangdol.report.presentation.dto.response.AssetResponse;
import zzangdol.report.presentation.dto.response.ReportEmotionResponse;
import zzangdol.report.presentation.dto.response.ReportResponse;
import zzangdol.user.domain.AuthProvider;
import zzangdol.user.domain.Role;
import zzangdol.user.domain.User;

@WebMvcTest(controllers = ReportController.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReportFacade reportFacade;

    @MockBean
    private AuthenticationArgumentResolver authenticationArgumentResolver;

    @WithMockUser(username = "회원", roles = {"MEMBER"})
    @DisplayName("[POST] 리포트 생성 테스트 - 정상")
    @Test
    void createReport() throws Exception {
        // given
        User user = User.builder()
                .email("test@example.com")
                .password("password")
                .role(Role.MEMBER)
                .authProvider(AuthProvider.DEFAULT)
                .notificationTime(LocalTime.now())
                .build();

        Long expectedReportId = 42L;
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("year", "2024");
        param.add("month", "5");
        param.add("week", "1");

        when(reportFacade.createReport(any(User.class), anyInt(), anyInt(), anyInt())).thenReturn(expectedReportId);
        when(authenticationArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(user);
        when(authenticationArgumentResolver.supportsParameter(any())).thenReturn(true);

        // when & then
        mockMvc.perform(post("/api/reports")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(param))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(42L));
    }

    @WithMockUser(username = "회원", roles = {"MEMBER"})
    @DisplayName("[GET] 리포트 단건 조회 테스트 - 정상")
    @Test
    void getReportByUser() throws Exception {
        // given
        ReportResponse reportResponse = ReportResponse.builder()
                .id(1L)
                .asset(AssetResponse.builder().url("test@example.com").description("Sample Description").build())
                .emotions(List.of(
                        ReportEmotionResponse.builder().name("happy").percentage(60).build(),
                        ReportEmotionResponse.builder().name("sad").percentage(20).build(),
                        ReportEmotionResponse.builder().name("angry").percentage(20).build()

                ))
                .positivePercentage(40)
                .negativePercentage(60)
                .build();

        User user = User.builder()
                .email("test@example.com")
                .password("password")
                .role(Role.MEMBER)
                .authProvider(AuthProvider.DEFAULT)
                .notificationTime(LocalTime.now())
                .build();

        when(reportFacade.getReportByUser(any(User.class), eq(1L))).thenReturn(reportResponse);
        when(authenticationArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(user);
        when(authenticationArgumentResolver.supportsParameter(any())).thenReturn(true);

        // when & then
        mockMvc.perform(get("/api/reports/{reportId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").value(1L))
                .andExpect(jsonPath("$.result.asset.url").value("test@example.com"))
                .andExpect(jsonPath("$.result.asset.description").value("Sample Description"))
                .andExpect(jsonPath("$.result.emotions[0].name").value("happy"))
                .andExpect(jsonPath("$.result.emotions[0].percentage").value(60))
                .andExpect(jsonPath("$.result.emotions[1].name").value("sad"))
                .andExpect(jsonPath("$.result.emotions[1].percentage").value(20))
                .andExpect(jsonPath("$.result.emotions[2].name").value("angry"))
                .andExpect(jsonPath("$.result.emotions[2].percentage").value(20))
                .andExpect(jsonPath("$.result.positivePercentage").value(40))
                .andExpect(jsonPath("$.result.negativePercentage").value(60));
    }

}