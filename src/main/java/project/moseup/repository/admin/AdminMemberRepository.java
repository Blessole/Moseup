package project.moseup.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;

import java.util.List;

public interface AdminMemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByMemberDelete(DeleteStatus FALSE);

    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);


}
