package project.moseup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import project.moseup.domain.Team;

public interface TeamSearchRepository extends JpaRepository<Team, Long>{
	
	//나중에 검색 필터 정할 때 마지막 부분만 바꾸면 됨 (현재 팀장 이름 제외 팀명or카테고리 포함된걸로 검색 가능)
	@Query(value = "SELECT * FROM teams t WHERE t.team_name like %:keyword% or t.team_category1 like %:keyword% or t.team_category2 like %:keyword% or t.team_category3 like %:keyword% order by t.team_no;", nativeQuery = true)
	List<Team> findAllSearch(@Param("keyword") String keyword);

//	List<Team> findByteamNameContaining(String keyword);
//
//	List<Team> findByteamCategory1Containing(String keyword);
}
