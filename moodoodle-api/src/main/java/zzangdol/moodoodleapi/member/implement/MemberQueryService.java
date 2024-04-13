package zzangdol.moodoodleapi.member.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.member.dao.MemberRepository;
import zzangdol.member.domain.Member;
import zzangdol.moodoodlecommon.exception.custom.MemberNotFoundException;

@Transactional
@RequiredArgsConstructor
@Service
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException());
    }

}
