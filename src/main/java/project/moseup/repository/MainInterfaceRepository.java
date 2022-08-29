package project.moseup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import project.moseup.domain.Team;

@Repository
public interface MainInterfaceRepository extends JpaRepository<Team, Long>{

	List<Team> findTop5ByOrderByTeamVolumeDesc();

	//카테고리1 공부 인기순
	@Query(value = "select * from teams where team_category1 = '공부' order by team_volume Desc limit 4", nativeQuery = true)
	List<Team> studyTopList();
	//카테고리1 운동 인기순
	@Query(value = "select * from teams where team_category1 = '운동' order by team_volume Desc limit 4", nativeQuery = true)
	List<Team> exerciseTopList();
	//카테고리1 습관 인기순
	@Query(value = "select * from teams where team_category1 = '습관' order by team_volume Desc limit 4", nativeQuery = true)
	List<Team> habitTopList();
	//카테고리1 기타 인기순
	@Query(value = "select * from teams where team_category1 = 'ETC' order by team_volume Desc limit 4", nativeQuery = true)
	List<Team> etcTopList();
	//최근 생성 4개팀
	List<Team> findTop4ByOrderByTnoDesc();
	//가입 인원 많은순 5개팀
	@Query(value = "select * from Teams t join team_Members m where t.team_no= m.team_no group by m.team_no order by count(m.team_no) desc limit 5;", nativeQuery = true)
	List<Team> topList();
	
	//가입 인원 많은순 5개팀
//	@Query(value = "select * from teams t where t.team_no in (select tm.team_no from (select * from team_members tm group by tm.team_no Order by count(tm.team_no) desc limit 5)as tm)", nativeQuery = true)
//	List<Team> topListKs();	//솔님이 만들어주신 쿼리
}
