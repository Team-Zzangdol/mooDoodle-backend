package zzangdol.diary.presentation;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

}