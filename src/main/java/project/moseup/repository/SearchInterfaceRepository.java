package project.moseup.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import project.moseup.domain.Team;

public interface SearchInterfaceRepository extends JpaRepository<Team, Long>{

	//기본 검색창 최신순
	Page<Team> findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3Containing(String teamLeader, String teamName, String teamCategory1, String teamCategory2, String teamCategory3, Pageable pageable);

	//필터2 최신순만 검색
	Page<Team> findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(
			String keyword, String keyword2, String keyword3, String keyword4, String keyword5, Pageable pageable);	
	//필터2 팀원수순만
	Page<Team> findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamVolumeDesc(
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
	Page<Team> findByTeamNameContainingOrderByTeamVolumeDesc(String keyword, Pageable pageable);
	//필터1 팀명, 필터2 예치금순 검색
	Page<Team> findByTeamNameContainingOrderByTeamDepositAsc(String keyword, Pageable pageable);
	
	//필터1 팀장명, 필터2 시작일순 검색
	Page<Team> findByTeamLeaderContainingOrderByStartDateAsc(String keyword, Pageable pageable);
	//필터1 팀장명, 필터2 시작일순 검색
	Page<Team> findByTeamLeaderContainingOrderByTeamVolumeDesc(String keyword, Pageable pageable);
	//필터1 팀장명, 필터2 예치금순 검색
	Page<Team> findByTeamLeaderContainingOrderByTeamDepositAsc(String keyword, Pageable pageable);

	//필터1 태그, 필터2 시작일순 검색
	Page<Team> findByTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByStartDateAsc(
			String keyword, String keyword2, String keyword3, Pageable pageable);
	//필터1 태그, 필터2 팀원수순 검색
	Page<Team> findByTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamVolumeDesc(
			String keyword, String keyword2, String keyword3, Pageable pageable);
	//필터1 태그, 필터2 예치금순 검색
	Page<Team> findByTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamDepositAsc(
			String keyword, String keyword2, String keyword3, Pageable pageable);

	//필터1 팀명/팀장명, 필터2 시작일순 검색
	Page<Team> findByTeamNameContainingOrTeamLeaderContainingOrderByStartDateAsc(String keyword, String keyword2,
			Pageable pageable);
	//필터1 팀명/팀장명, 필터2 팀원수순 검색
	Page<Team> findByTeamNameContainingOrTeamLeaderContainingOrderByTeamVolumeDesc(String keyword, String keyword2,
			Pageable pageable);
	//필터1 팀명/팀장명, 필터2 예치금순 검색
	Page<Team> findByTeamNameContainingOrTeamLeaderContainingOrderByTeamDepositAsc(String keyword, String keyword2,
			Pageable pageable);

	//필터1 팀명/태그, 필터2 시작일순 검색
	Page<Team> findByTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByStartDateAsc(
			String keyword, String keyword2, String keyword3, String keyword4, Pageable pageable);
	//필터1 팀명/태그, 필터2 팀원수순 검색
	Page<Team> findByTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamVolumeDesc(
			String keyword, String keyword2, String keyword3, String keyword4, Pageable pageable);
	//필터1 팀명/태그, 필터2 예치금순 검색
	Page<Team> findByTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamDepositAsc(
			String keyword, String keyword2, String keyword3, String keyword4, Pageable pageable);

	//필터1 팀장명/태그, 필터2 시작일순 검색
	Page<Team> findByTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByStartDateAsc(
			String keyword, String keyword2, String keyword3, String keyword4, Pageable pageable);
	//필터1 팀장명/태그, 필터2 팀원수순 검색
	Page<Team> findByTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamVolumeDesc(
			String keyword, String keyword2, String keyword3, String keyword4, Pageable pageable);
	//필터1 팀장명/태그, 필터2 예치금순 검색
	Page<Team> findByTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamDepositAsc(
			String keyword, String keyword2, String keyword3, String keyword4, Pageable pageable);

	//필터1 팀장/팀장명/태그, 필터2 시작일순 검색
	Page<Team> findByTeamNameContainingOrTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByStartDateAsc(
			String keyword, String keyword2, String keyword3, String keyword4, String keyword5, Pageable pageable);
	//필터1 팀장/팀장명/태그, 필터2 팀원수순 검색
	Page<Team> findByTeamNameContainingOrTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamVolumeDesc(
			String keyword, String keyword2, String keyword3, String keyword4, String keyword5, Pageable pageable);
	//필터1 팀장/팀장명/태그, 필터2 예치금순 검색
	Page<Team> findByTeamNameContainingOrTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamDepositAsc(
			String keyword, String keyword2, String keyword3, String keyword4, String keyword5, Pageable pageable);

}
