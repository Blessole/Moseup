package project.moseup.repository.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.Role;

import java.time.LocalDate;

public interface AdminMemberRepository extends JpaRepository<Member, Long> {

    // 유효성(중복)검사
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    Page<Member> findByEmailContainingOrNameContainingOrNicknameContaining(String email, String name, String nickname, Pageable pageable);

    Page<Member> findAll(Pageable pageable);

    Page<Member> findByMemberDelete(DeleteStatus mTrue, Pageable pageable);

    Page<Member> findByRole(Role admin, Pageable pageable);

    Page<Member> findByMemberDateBetween(LocalDate start, LocalDate end, Pageable pageable);
}
