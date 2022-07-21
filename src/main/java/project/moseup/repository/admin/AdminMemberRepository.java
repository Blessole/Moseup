package project.moseup.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.Member;

public interface AdminMemberRepository extends JpaRepository<Member, Long> {

}
