package project.moseup.repository.teampage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import project.moseup.domain.TeamAskBoard;

public interface TeamAskBoardPageRepository extends JpaRepository<TeamAskBoard, Long>{
	
	@Modifying
	@Query("update TeamAskBoard a set a.teamAskReadCount = a.teamAskReadCount + 1 where a.tano = :tano") 
	int updateReadCount(@Param("tano") Long tano);
}
