package project.moseup.repository.teampage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.moseup.domain.CheckBoard;
import project.moseup.domain.Member;
import project.moseup.domain.Team;

import java.util.List;

public interface CheckBoardPageRepository extends JpaRepository<CheckBoard, Long> {
	
	// 해당 팀으로 조회 후 리스트 나열
	Page<CheckBoard> findByTeam(Team team, Pageable pageable);
	
	// 조회수 증가 로직
	@Modifying
	@Query("update CheckBoard c set c.checkReadCount = c.checkReadCount + 1 where c.cno = :cno") 
	int updateReadCount(@Param("cno") Long cno);
	
	List<CheckBoard> findByTeam(Team team);
	
	int countByTeamAndMember(Team team, Member member);

	List<CheckBoard> findByMemberOrderByCnoDesc(Member memberPS);

	List<CheckBoard> findByTeamOrderByCnoDesc(Team team);
	
}
