package project.moseup.repository.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.Team;

import java.lang.reflect.Member;
import java.util.List;

public interface AdminTeamRepository extends JpaRepository<Team, Long> {

    Page<Team> findByTeamNameContaining(String keyword, Pageable pageable);

    List<Member> findMember(Long mno);
}
