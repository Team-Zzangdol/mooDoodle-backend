package zzangdol.moodoodleapi.auth.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.user.dao.UserRepository;
import zzangdol.user.domain.AuthProvider;
import zzangdol.user.domain.User;
import zzangdol.user.domain.Role;
import zzangdol.moodoodleapi.auth.presentation.dto.request.SignInRequest;
import zzangdol.moodoodleapi.auth.presentation.dto.request.SignUpRequest;
import zzangdol.moodoodleapi.jwt.JwtResponse;
import zzangdol.moodoodleapi.jwt.JwtService;
import zzangdol.moodoodlecommon.exception.custom.MemberCredentialsException;
import zzangdol.moodoodlecommon.response.status.ErrorStatus;

@Transactional
@RequiredArgsConstructor
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public JwtResponse signUp(SignUpRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = userRepository.save(request.toEntity(AuthProvider.DEFAULT, Role.MEMBER, encodedPassword));
        return jwtService.issueToken(user);
    }

    public JwtResponse signIn(SignInRequest request) {
        User user = userRepository.findByAuthProviderAndEmail(AuthProvider.DEFAULT, request.getEmail())
                .orElseThrow(() -> new MemberCredentialsException(ErrorStatus.MEMBER_NOT_FOUND));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new MemberCredentialsException(ErrorStatus.PASSWORD_MISMATCH);
        }
        return jwtService.issueToken(user);
    }

    public boolean isEmailAvailable(String email) {
        return !userRepository.findByAuthProviderAndEmail(AuthProvider.DEFAULT, email).isPresent();
    }

}
