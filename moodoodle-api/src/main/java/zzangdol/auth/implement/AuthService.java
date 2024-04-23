package zzangdol.auth.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.auth.presentation.dto.request.SignInRequest;
import zzangdol.auth.presentation.dto.request.SignUpRequest;
import zzangdol.exception.custom.UserCredentialsException;
import zzangdol.jwt.JwtResponse;
import zzangdol.jwt.JwtService;
import zzangdol.response.status.ErrorStatus;
import zzangdol.user.dao.UserRepository;
import zzangdol.user.domain.AuthProvider;
import zzangdol.user.domain.Role;
import zzangdol.user.domain.User;

@Transactional
@RequiredArgsConstructor
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

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

}