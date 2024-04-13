package zzangdol.member.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zzangdol.member.domain.AuthProvider;
import zzangdol.member.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByAuthProviderAndEmail(AuthProvider authProvider, String email);

}
