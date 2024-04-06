package zzangdol.member.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzangdol.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
