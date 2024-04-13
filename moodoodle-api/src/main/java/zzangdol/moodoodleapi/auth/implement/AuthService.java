package zzangdol.moodoodleapi.auth.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.member.dao.MemberRepository;
import zzangdol.member.domain.AuthProvider;
import zzangdol.member.domain.Member;
import zzangdol.member.domain.Role;
import zzangdol.moodoodleapi.auth.presentation.dto.request.SignInRequest;
import zzangdol.moodoodleapi.auth.presentation.dto.request.SignUpRequest;
import zzangdol.moodoodleapi.jwt.JwtProvider;
import zzangdol.moodoodleapi.jwt.JwtResponse;
import zzangdol.moodoodlecommon.exception.custom.MemberCredentialsException;
import zzangdol.moodoodlecommon.response.status.ErrorStatus;

@Transactional
@RequiredArgsConstructor
@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public JwtResponse signUp(SignUpRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Member member = memberRepository.save(request.toEntity(AuthProvider.DEFAULT, Role.USER, encodedPassword));
        Authentication authentication = new UsernamePasswordAuthenticationToken(member, "", member.getAuthorities());
        return jwtProvider.generateToken(authentication);
    }

    public JwtResponse signIn(SignInRequest request) {
        Member member = memberRepository.findByAuthProviderAndEmail(AuthProvider.DEFAULT, request.getEmail())
                .orElseThrow(() -> new MemberCredentialsException(ErrorStatus.MEMBER_NOT_FOUND));
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new MemberCredentialsException(ErrorStatus.PASSWORD_MISMATCH);
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(member, "", member.getAuthorities());
        return jwtProvider.generateToken(authentication);
    }

    public boolean isEmailAvailable(String email) {
        return !memberRepository.findByAuthProviderAndEmail(AuthProvider.DEFAULT, email).isPresent();
    }

}
