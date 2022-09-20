package project.moseup.repository.teampage;

import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.Team;
import project.moseup.domain.TeamBankbookDetail;

import java.util.List;

public interface TeamBankbookDetailRepository extends JpaRepository<TeamBankbookDetail, Long> {

    List<TeamBankbookDetail> findByTeamBankbookTeamOrderByTdnoDesc(Team teamPS);
}
