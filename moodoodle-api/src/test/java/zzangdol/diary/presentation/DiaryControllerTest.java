package zzangdol.diary.presentation;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import zzangdol.diary.business.DiaryFacade;
import zzangdol.diary.presentation.dto.request.DiaryCreateRequest;
import zzangdol.diary.presentation.dto.request.DiaryUpdateRequest;
import zzangdol.diary.presentation.dto.response.DiaryListResponse;
import zzangdol.diary.presentation.dto.response.DiaryResponse;
import zzangdol.global.annotation.AuthenticationArgumentResolver;
import zzangdol.user.domain.AuthProvider;
import zzangdol.user.domain.Role;
import zzangdol.user.domain.User;

@WebMvcTest(controllers = DiaryController.class)
class DiaryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DiaryFacade diaryFacade;

    @MockBean
    private AuthenticationArgumentResolver authenticationArgumentResolver;

    @WithMockUser(username = "회원", roles = {"MEMBER"})
    @DisplayName("[POST] 일기 생성 테스트 - 정상")
    @Test
    void createDiary() throws Exception {
        // given
        DiaryCreateRequest request = DiaryCreateRequest.builder()
                .content("content")
                .date(LocalDateTime.of(2024, 1, 1, 0, 0))
                .imageUrl("imageUrl")
                .build();

        User user = User.builder()
                .email("test@example.com")
                .password("password")
                .role(Role.MEMBER)
                .authProvider(AuthProvider.DEFAULT)
                .notificationTime(LocalTime.now())
                .build();

        Long expectedDiaryId = 42L;
        when(diaryFacade.createDiary(any(User.class), any(DiaryCreateRequest.class))).thenReturn(expectedDiaryId);
        when(authenticationArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(user);
        when(authenticationArgumentResolver.supportsParameter(any())).thenReturn(true);

        // when & then
        mockMvc.perform(post("/api/diaries")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(42L));
    }

    @WithMockUser(username = "회원", roles = {"MEMBER"})
    @DisplayName("[POST] 일기 생성 테스트 - date 누락")
    @Test
    void createDiaryWithoutDate() throws Exception {
        // given
        DiaryCreateRequest request = DiaryCreateRequest.builder()
                .content("content")
                .imageUrl("imageUrl")
                .build();

        User user = User.builder()
                .email("test@example.com")
                .password("password")
                .role(Role.MEMBER)
                .authProvider(AuthProvider.DEFAULT)
                .notificationTime(LocalTime.now())
                .build();

        Long expectedDiaryId = 42L;
        when(diaryFacade.createDiary(any(User.class), any(DiaryCreateRequest.class))).thenReturn(expectedDiaryId);
        when(authenticationArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(user);
        when(authenticationArgumentResolver.supportsParameter(any())).thenReturn(true);

        // when & then
        mockMvc.perform(post("/api/diaries")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.code").value(4000))
                .andExpect(jsonPath("$.result.date").value("date는 필수값입니다."));
    }

    @WithMockUser(username = "회원", roles = {"MEMBER"})
    @DisplayName("[POST] 일기 생성 테스트 - content 누락")
    @Test
    void createDiaryWithoutContent() throws Exception {
        // given
        DiaryCreateRequest request = DiaryCreateRequest.builder()
                .content("")
                .date(LocalDateTime.of(2024, 1, 1, 0, 0))
                .imageUrl("imageUrl")
                .build();

        User user = User.builder()
                .email("test@example.com")
                .password("password")
                .role(Role.MEMBER)
                .authProvider(AuthProvider.DEFAULT)
                .notificationTime(LocalTime.now())
                .build();

        Long expectedDiaryId = 42L;
        when(diaryFacade.createDiary(any(User.class), any(DiaryCreateRequest.class))).thenReturn(expectedDiaryId);
        when(authenticationArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(user);
        when(authenticationArgumentResolver.supportsParameter(any())).thenReturn(true);

        // when & then
        mockMvc.perform(post("/api/diaries")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.code").value(4000))
                .andExpect(jsonPath("$.result.content").value("content는 필수값입니다."));
    }

    @WithMockUser(username = "회원", roles = {"MEMBER"})
    @DisplayName("[PATCH] 일기 수정 테스트 - 정상")
    @Test
    void updateDiary() throws Exception {
        // given
        DiaryUpdateRequest request = DiaryUpdateRequest.builder()
                .content("content")
                .date(LocalDateTime.of(2024, 1, 1, 0, 0))
                .build();

        User user = User.builder()
                .email("test@example.com")
                .password("password")
                .role(Role.MEMBER)
                .authProvider(AuthProvider.DEFAULT)
                .notificationTime(LocalTime.now())
                .build();

        Long expectedDiaryId = 42L;
        when(diaryFacade.updateDiary(any(User.class), any(Long.class), any(DiaryUpdateRequest.class))).thenReturn(
                expectedDiaryId);
        when(authenticationArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(user);
        when(authenticationArgumentResolver.supportsParameter(any())).thenReturn(true);

        // when & then
        mockMvc.perform(patch("/api/diaries/{diaryId}", expectedDiaryId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(expectedDiaryId));
    }

    @WithMockUser(username = "회원", roles = {"MEMBER"})
    @DisplayName("[DELETE] 일기 삭제 테스트 - 정상")
    @Test
    void deleteDiary() throws Exception {
        // given

        Long diaryId = 42L;
        User user = User.builder()
                .email("test@example.com")
                .password("password")
                .role(Role.MEMBER)
                .authProvider(AuthProvider.DEFAULT)
                .notificationTime(LocalTime.now())
                .build();

        doNothing().when(diaryFacade).deleteDiary(user, diaryId);
        when(authenticationArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(user);
        when(authenticationArgumentResolver.supportsParameter(any())).thenReturn(true);

        // when & then
        mockMvc.perform(delete("/api/diaries/{diaryId}", diaryId)
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(diaryFacade).deleteDiary(user, diaryId);
    }

    @WithMockUser(username = "회원", roles = {"MEMBER"})
    @DisplayName("[GET] 일기 단건 조회 테스트 - 정상")
    @Test
    void getDiaryByUser() throws Exception {
        // given
        DiaryResponse diaryResponse = DiaryResponse.builder()
                .id(1L)
                .date(LocalDateTime.of(2024, 1, 1, 0, 0))
                .content("content")
                .imageUrl("imageUrl")
                .color("#FFFFFF")
                .build();

        User user = User.builder()
                .email("test@example.com")
                .password("password")
                .role(Role.MEMBER)
                .authProvider(AuthProvider.DEFAULT)
                .notificationTime(LocalTime.now())
                .build();

        when(diaryFacade.getDiaryByUser(any(User.class), eq(1L))).thenReturn(diaryResponse);
        when(authenticationArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(user);
        when(authenticationArgumentResolver.supportsParameter(any())).thenReturn(true);

        // when & then
        mockMvc.perform(get("/api/diaries/{diaryId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").value(1L))
                .andExpect(jsonPath("$.result.content").value("content"))
                .andExpect(jsonPath("$.result.imageUrl").value("imageUrl"))
                .andExpect(jsonPath("$.result.color").value("#FFFFFF"));
    }

    @WithMockUser(username = "회원", roles = {"MEMBER"})
    @DisplayName("[GET] 월간 일기 목록 조회 테스트 - 정상")
    @Test
    void getMonthlyDiaries() throws Exception {
        // given
        DiaryListResponse diaryListResponse = DiaryListResponse.builder()
                .diaries(Arrays.asList(
                        DiaryResponse.builder()
                                .id(1L)
                                .date(LocalDateTime.of(2024, 4, 1, 0, 0))
                                .content("content1")
                                .imageUrl("imageUrl1")
                                .color("#111111")
                                .build(),
                        DiaryResponse.builder()
                                .id(2L)
                                .date(LocalDateTime.of(2024, 4, 20, 0, 0))
                                .content("content2")
                                .imageUrl("imageUrl2")
                                .color("#222222")
                                .build()
                ))
                .build();

        User user = User.builder()
                .email("test@example.com")
                .password("password")
                .role(Role.MEMBER)
                .authProvider(AuthProvider.DEFAULT)
                .notificationTime(LocalTime.now())
                .build();

        when(diaryFacade.getMonthlyDiariesByUser(any(User.class), eq(2024), eq(4))).thenReturn(diaryListResponse);
        when(authenticationArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(user);
        when(authenticationArgumentResolver.supportsParameter(any())).thenReturn(true);

        // when & then
        mockMvc.perform(get("/api/diaries")
                        .param("year", "2024")
                        .param("month", "4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.diaries[0].id").value(1L))
                .andExpect(jsonPath("$.result.diaries[0].content").value("content1"))
                .andExpect(jsonPath("$.result.diaries[1].id").value(2L))
                .andExpect(jsonPath("$.result.diaries[1].content").value("content2"));
    }

}