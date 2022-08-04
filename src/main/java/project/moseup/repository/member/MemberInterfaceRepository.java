package project.moseup.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.Member;

import java.util.Optional;

public interface MemberInterfaceRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

}
