package project.moseup.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import project.moseup.domain.Team;

public interface SearchRepository extends JpaRepository<Team, Long>{

	//나중에 검색 필터 정할 때 order by만 바꾸면 됨
//	@Query(value = "SELECT * FROM teams t WHERE t.team_leader like %:keyword% or t.team_name like %:keyword% or t.team_category1 like %:keyword% or t.team_category2 like %:keyword% or t.team_category3 like %:keyword% order by t.team_no;", nativeQuery = true)
	//Page<Team> findAllSearch(@Param("keyword") String keyword, Pageable pageable);

	//기본 검색창 최신순
	Page<Team> findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3Containing(String teamLeader, String teamName, String teamCategory1, String teamCategory2, String teamCategory3, Pageable pageable);

	//필터2 최신순
	Page<Team> findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(
			String keyword, String keyword2, String keyword3, String keyword4, String keyword5, Pageable pageable);
	//필터2 학생순
	Page<Team> findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamVolumeDesc(
			String keyword, String keyword2, String keyword3, String keyword4, String keyword5, Pageable pageable);
	
	//필터2 예치금순
	Page<Team> findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamDepositAsc(
			String keyword, String keyword2, String keyword3, String keyword4, String keyword5, Pageable pageable);

	//필터2 날짜순 검색
	Page<Team> findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByStartDateAsc(
			String keyword, String keyword2, String keyword3, String keyword4, String keyword5, Pageable pageable);



}
