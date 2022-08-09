package project.moseup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import project.moseup.domain.Team;

public interface SearchRepository extends JpaRepository<Team, Long>{
	
	//나중에 검색 필터 정할 때 order by만 바꾸면 됨
	@Query(value = "SELECT * FROM teams t WHERE t.team_leader like %:keyword% or t.team_name like %:keyword% or t.team_category1 like %:keyword% or t.team_category2 like %:keyword% or t.team_category3 like %:keyword% order by t.team_no;", nativeQuery = true)
	List<Team> findAllSearch(@Param("keyword") String keyword);

}
