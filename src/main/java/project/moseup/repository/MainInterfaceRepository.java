package project.moseup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.moseup.domain.Team;

import java.util.List;

@Repository
public interface MainInterfaceRepository extends JpaRepository<Team, Long>{

	List<Team> findTop5ByOrderByTeamVolumeDesc();

	//카테고리1 공부 최신순
	@Query(value = "select * from teams where team_category1 = '공부' order by team_no Desc limit 4", nativeQuery = true)
	List<Team> studyTopList();
	//카테고리1 운동 최신순
	@Query(value = "select * from teams where team_category1 = '운동' order by team_no Desc limit 4", nativeQuery = true)
	List<Team> exerciseTopList();
	//카테고리1 습관 최신순
	@Query(value = "select * from teams where team_category1 = '습관' order by team_no Desc limit 4", nativeQuery = true)
	List<Team> habitTopList();
	//카테고리1 기타 최신순
	@Query(value = "select * from teams where team_category1 = 'ETC' order by team_no Desc limit 4", nativeQuery = true)
	List<Team> etcTopList();
	//최근 생성 4개팀
	List<Team> findTop4ByOrderByTnoDesc();
	//1.찜목록 2.가입 인원 많은순 5개팀
	@Query(value = "select t.* from Teams t join likes l where t.team_no= l.team_no group by l.team_no order by count(l.team_no) desc, t.team_joiner desc limit 5", nativeQuery = true)
	List<Team> topList();
}
