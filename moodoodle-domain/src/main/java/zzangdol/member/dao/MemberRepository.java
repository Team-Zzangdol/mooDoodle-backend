package zzangdol.member.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zzangdol.member.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
