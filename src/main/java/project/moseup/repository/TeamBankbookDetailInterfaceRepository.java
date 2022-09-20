package project.moseup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import project.moseup.domain.TeamBankbook;
import project.moseup.domain.TeamBankbookDetail;

public interface TeamBankbookDetailInterfaceRepository extends JpaRepository<TeamBankbookDetail, Long>{

	List<TeamBankbookDetail> findTop1ByTeamBankbookOrderByTdnoDesc(TeamBankbook teamBankbook);

}
