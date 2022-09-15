package project.moseup.repository.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.Team;

import java.time.LocalDate;

public interface AdminTeamRepository extends JpaRepository<Team, Long> {

    Page<Team> findByTeamNameContainingOrMemberNicknameContaining(String keyword, String keyword2, Pageable pageable);


    Page<Team> findByTeamDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<Team> findByEndDateIsBefore(LocalDate now, Pageable pageable);

    Page<Team> findByEndDateIsAfter(LocalDate now, Pageable pageable);
}
