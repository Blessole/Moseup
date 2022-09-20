package project.moseup.repository.teampage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.moseup.domain.Team;
import project.moseup.domain.TeamAskBoard;

import java.util.List;

public interface TeamAskBoardPageRepository extends JpaRepository<TeamAskBoard, Long>{
	
	// 조회수 증가 로직
	@Modifying
	@Query("update TeamAskBoard a set a.teamAskReadCount = a.teamAskReadCount + 1 where a.tano = :tano") 
	int updateReadCount(@Param("tano") Long tano);
	
	// 팀 번호로 조회(tno는 TeamAskBoard에 없다는 오류가 나서 Team team을 넣어서 해결)
	Page<TeamAskBoard> findByTeam(Team team, Pageable pageable);

    List<TeamAskBoard> findByTeamOrderByTanoDesc(Team team);
}
