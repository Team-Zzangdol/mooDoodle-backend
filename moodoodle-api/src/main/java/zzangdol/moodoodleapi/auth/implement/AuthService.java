package zzangdol.moodoodleapi.auth.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.member.dao.MemberRepository;
import zzangdol.member.domain.Member;
import zzangdol.member.domain.Role;
import zzangdol.member.domain.SocialProvider;
import zzangdol.moodoodleapi.auth.presentation.dto.request.SignUpRequest;

@Transactional
@RequiredArgsConstructor
@Service
public class AuthService {

    private final MemberRepository memberRepository;

    public Member signUp(SignUpRequest request) {
        return memberRepository.save(request.toEntity(SocialProvider.DEFAULT, Role.USER));
    }

}
