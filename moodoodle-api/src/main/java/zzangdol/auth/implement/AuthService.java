package zzangdol.auth.implement;

import java.util.EnumMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.auth.presentation.dto.request.SignInRequest;
import zzangdol.auth.presentation.dto.request.SignUpRequest;
import zzangdol.auth.presentation.dto.request.SocialLoginRequest;
import zzangdol.exception.GeneralException;
import zzangdol.exception.custom.UserCredentialsException;
import zzangdol.jwt.JwtResponse;
import zzangdol.jwt.JwtService;
import zzangdol.oauth.client.SocialLoginClient;
import zzangdol.oauth.dto.KakaoUserInfoResponse;
import zzangdol.oauth.dto.SocialUserInfoResponse;
import zzangdol.response.status.ErrorStatus;
import zzangdol.user.dao.UserRepository;
import zzangdol.user.domain.AuthProvider;
import zzangdol.user.domain.Role;
import zzangdol.user.domain.User;

@Slf4j
@Transactional
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final Map<AuthProvider, SocialLoginClient<? extends SocialUserInfoResponse>> socialLoginClients;

    private static final int RANDOM_PASSWORD_LENGTH = 10;

    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtService jwtService,
                       SocialLoginClient<KakaoUserInfoResponse> kakaoLoginClient /*, SocialLoginClient<NaverUserInfoResponse> naverLoginClient */) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.socialLoginClients = new EnumMap<>(AuthProvider.class);
        this.socialLoginClients.put(AuthProvider.KAKAO, kakaoLoginClient);
        // this.socialLoginClients.put(AuthProvider.NAVER, naverLoginClient);
    }

    public User signUp(SignUpRequest request) {
        User user = buildUser(request);
        return userRepository.save(user);
    }

    private User buildUser(SignUpRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        return User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .nickname(request.getNickname())
                .authProvider(AuthProvider.DEFAULT)
                .role(Role.MEMBER)
                .notificationTime(request.getNotificationTime())
                .build();
    }

    public JwtResponse signIn(SignInRequest request) {
        User user = userRepository.findByAuthProviderAndEmail(AuthProvider.DEFAULT, request.getEmail())
                .orElseThrow(() -> UserCredentialsException.EXCEPTION);
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserCredentialsException(ErrorStatus.PASSWORD_MISMATCH);
        }
        return jwtService.issueToken(user);
    }

    public boolean isEmailAvailable(String email) {
        return !userRepository.findByAuthProviderAndEmail(AuthProvider.DEFAULT, email).isPresent();
    }

    public JwtResponse socialLogin(SocialLoginRequest request) {
        AuthProvider authProvider = request.getAuthProvider();
        SocialLoginClient<? extends SocialUserInfoResponse> client = socialLoginClients.get(authProvider);
        if (client == null) {
            throw new GeneralException(ErrorStatus.AUTH_PROVIDER_NOT_SUPPORTED);
        }

        SocialUserInfoResponse userInfo = client.getUserInfo(request.getAuthorizationCode());

        return userRepository.findByAuthProviderAndEmail(authProvider, userInfo.getEmail())
                .map(jwtService::issueToken)
                .orElseGet(() -> createOauthUserAndIssueToken(userInfo, authProvider));
    }

    private JwtResponse createOauthUserAndIssueToken(SocialUserInfoResponse userInfo, AuthProvider authProvider) {
        String randomPassword = RandomStringUtils.randomAlphanumeric(RANDOM_PASSWORD_LENGTH);
        String encodedPassword = passwordEncoder.encode(randomPassword);

        User newUser = User.builder()
                .email(userInfo.getEmail())
                .password(encodedPassword)
                .nickname(userInfo.getNickname())
                .authProvider(authProvider)
                .role(Role.MEMBER)
                .build();

        userRepository.save(newUser);
        return jwtService.issueToken(newUser);
    }

}
