package project.moseup.repository.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.Member;

public interface AdminMemberRepository extends JpaRepository<Member, Long> {

    // 유효성(중복)검사
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    // 회원리스트
    Page<Member> findByEmailContainingOrNameContainingOrNicknameContaining(String Email, String name, String nickname, Pageable pageable);

    Page<Member> findAll(Pageable pageable);



}
