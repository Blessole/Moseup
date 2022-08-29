package project.moseup.repository.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.Team;

public interface AdminTeamRepository extends JpaRepository<Team, Long> {

    Page<Team> findByTeamNameContainingOrMemberNicknameContaining(String keyword, String keyword2, Pageable pageable);


}
