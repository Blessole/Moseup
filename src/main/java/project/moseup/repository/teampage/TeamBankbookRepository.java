package project.moseup.repository.teampage;

import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.Team;
import project.moseup.domain.TeamBankbook;

public interface TeamBankbookRepository extends JpaRepository<TeamBankbook, Long> {


    TeamBankbook findByTeam(Team team);

    TeamBankbook findByTeamOrderByTeamBankbookDetailsTdnoDesc(Team team);
}
