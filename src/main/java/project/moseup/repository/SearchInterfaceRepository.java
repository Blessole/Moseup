package project.moseup.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import project.moseup.domain.Team;

public interface SearchInterfaceRepository extends JpaRepository<Team, Long>{

	//기본 검색창 최신순
	Page<Team> findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3Containing(String teamLeader, String teamName, String teamCategory1, String teamCategory2, String teamCategory3, Pageable pageable);

	//필터2 최신순만 검색
	Page<Team> findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(
			String keyword, String keyword2, String keyword3, String keyword4, String keyword5, Pageable pageable);	
	//필터2 팀원수순만
	Page<Team> findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamJoinerDesc(
			String keyword, String keyword2, String keyword3, String keyword4, String keyword5, Pageable pageable);	
	//필터2 예치금순만 검색
	Page<Team> findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamDepositAsc(
			String keyword, String keyword2, String keyword3, String keyword4, String keyword5, Pageable pageable);
	//필터2 시작일순만 검색
	Page<Team> findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByStartDateAsc(
			String keyword, String keyword2, String keyword3, String keyword4, String keyword5, Pageable pageable);
	
	//필터1 팀명만 검색
	Page<Team> findByTeamNameContainingOrderByTnoDesc(String keyword, Pageable pageable);
	//필터1 팀장명만 검색
	Page<Team> findByTeamLeaderContainingOrderByTnoDesc(String keyword, Pageable pageable);	
	//필터1 태그만 검색
	Page<Team> findByTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(
			String keyword, String keyword2, String keyword3, Pageable pageable);
	
	//필터1 팀명/팀장명 검색
	Page<Team> findByTeamNameContainingOrTeamLeaderContainingOrderByTnoDesc(String keyword, String keyword2,
			Pageable pageable);
	//필터1 팀명/태그 검색
	Page<Team> findByTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(
			String keyword, String keyword2, String keyword3, String keyword4, Pageable pageable);
	//필터1 팀장명/태그 검색
	Page<Team> findByTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(
			String keyword, String keyword2, String keyword3, String keyword4, Pageable pageable);
	//필터1 팀장/팀장명/태그 검색
	Page<Team> findByTeamNameContainingOrTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(
			String keyword, String keyword2, String keyword3, String keyword4, String keyword5, Pageable pageable);

	//필터1 팀명, 필터2 시작일순 검색
	Page<Team> findByTeamNameContainingOrderByStartDateAsc(String keyword, Pageable pageable);
	//필터1 팀명, 필터2 팀원수순 검색
	Page<Team> findByTeamNameContainingOrderByTeamJoinerDesc(String keyword, Pageable pageable);
	//필터1 팀명, 필터2 예치금순 검색
	Page<Team> findByTeamNameContainingOrderByTeamDepositAsc(String keyword, Pageable pageable);
	
	//필터1 팀장명, 필터2 시작일순 검색
	Page<Team> findByTeamLeaderContainingOrderByStartDateAsc(String keyword, Pageable pageable);
	//필터1 팀장명, 필터2 팀원수순 검색
	Page<Team> findByTeamLeaderContainingOrderByTeamJoinerDesc(String keyword, Pageable pageable);
	//필터1 팀장명, 필터2 예치금순 검색
	Page<Team> findByTeamLeaderContainingOrderByTeamDepositAsc(String keyword, Pageable pageable);

	//필터1 태그, 필터2 시작일순 검색
	Page<Team> findByTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByStartDateAsc(
			String keyword, String keyword2, String keyword3, Pageable pageable);
	//필터1 태그, 필터2 팀원수순 검색
	Page<Team> findByTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamJoinerDesc(
			String keyword, String keyword2, String keyword3, Pageable pageable);
	//필터1 태그, 필터2 예치금순 검색
	Page<Team> findByTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamDepositAsc(
			String keyword, String keyword2, String keyword3, Pageable pageable);

	//필터1 팀명/팀장명, 필터2 시작일순 검색
	Page<Team> findByTeamNameContainingOrTeamLeaderContainingOrderByStartDateAsc(String keyword, String keyword2,
			Pageable pageable);
	//필터1 팀명/팀장명, 필터2 팀원수순 검색
	Page<Team> findByTeamNameContainingOrTeamLeaderContainingOrderByTeamJoinerDesc(String keyword, String keyword2,
			Pageable pageable);
	//필터1 팀명/팀장명, 필터2 예치금순 검색
	Page<Team> findByTeamNameContainingOrTeamLeaderContainingOrderByTeamDepositAsc(String keyword, String keyword2,
			Pageable pageable);

	//필터1 팀명/태그, 필터2 시작일순 검색
	Page<Team> findByTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByStartDateAsc(
			String keyword, String keyword2, String keyword3, String keyword4, Pageable pageable);
	//필터1 팀명/태그, 필터2 팀원수순 검색
	Page<Team> findByTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamJoinerDesc(
			String keyword, String keyword2, String keyword3, String keyword4, Pageable pageable);
	//필터1 팀명/태그, 필터2 예치금순 검색
	Page<Team> findByTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamDepositAsc(
			String keyword, String keyword2, String keyword3, String keyword4, Pageable pageable);

	//필터1 팀장명/태그, 필터2 시작일순 검색
	Page<Team> findByTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByStartDateAsc(
			String keyword, String keyword2, String keyword3, String keyword4, Pageable pageable);
	//필터1 팀장명/태그, 필터2 팀원수순 검색
	Page<Team> findByTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamJoinerDesc(
			String keyword, String keyword2, String keyword3, String keyword4, Pageable pageable);
	//필터1 팀장명/태그, 필터2 예치금순 검색
	Page<Team> findByTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamDepositAsc(
			String keyword, String keyword2, String keyword3, String keyword4, Pageable pageable);

	//필터1 팀장/팀장명/태그, 필터2 시작일순 검색
	Page<Team> findByTeamNameContainingOrTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByStartDateAsc(
			String keyword, String keyword2, String keyword3, String keyword4, String keyword5, Pageable pageable);
	//필터1 팀장/팀장명/태그, 필터2 팀원수순 검색
	Page<Team> findByTeamNameContainingOrTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamJoinerDesc(
			String keyword, String keyword2, String keyword3, String keyword4, String keyword5, Pageable pageable);
	//필터1 팀장/팀장명/태그, 필터2 예치금순 검색
	Page<Team> findByTeamNameContainingOrTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamDepositAsc(
			String keyword, String keyword2, String keyword3, String keyword4, String keyword5, Pageable pageable);

	
	//메인페이지에서 카테고리1 인기순 팀리스트
	Page<Team> findByTeamCategory1OrderByTeamJoinerDesc(String keyword, Pageable pageable);
	//메인페이지 최신순 24개
	Page<Team> queryFirst24ByOrderByTnoDesc(Pageable pageable);

	//필터2만 팀원수순 하는중!
	@Query(value = "select t.* from teams t join team_members m where t.team_no= m.team_no and \r\n"
			+ "(t.team_name='대통령' or t.team_leader='대통령' or t.team_category1='대통령' or t.team_category1='대통령' or t.team_category3='대통령')\r\n"
			+ "group by m.team_no order by count(m.team_no) desc", nativeQuery = true)
	Page<Team> filter2SearchByTeamMember(String keyword, Pageable pageable);

}
