package project.moseup.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import project.moseup.domain.Team;

public interface CategoryInterfaceRepository extends JpaRepository<Team, Long>{
	
	//네비바에서 공부/운동/습관/기타 선택시 리스트(팀원수순)
	Page<Team> findByTeamCategory1ContainingOrderByTeamJoinerDesc(String keyword, Pageable pageable);

	//필터2 최신순
	Page<Team> findByTeamCategory1ContainingOrderByTnoDesc(String keyword, Pageable pageable);

	//best3 습관(찜 많은 순으로 바꿔야함)
	@Query(value = "select * from teams t where t.team_category1= ?1 order by t.team_joiner desc limit 3;", nativeQuery = true)
	List<Team> topList(String keyword);
	
	//필터1 예치금만 선택했을 때
	@Query(value = "select * from teams t where t.team_category1= ?1 and team_deposit between '1' and ?2 order by t.team_joiner desc", nativeQuery = true)
	Page<Team> depositList(String keyword, String deposit, Pageable pageable);
	//필터1 모집인원만 선택했을 때
	@Query(value = "select * from teams t where t.team_category1= ?1 and team_volume between '2' and ?2 order by t.team_joiner desc", nativeQuery = true)
	Page<Team> volumeList(String keyword, String volume, Pageable pageable);
	//필터1 미포함만 선택했을 때
	@Query(value = "select * from teams t where t.team_category1= ?1 and t.start_date >= date(now()) order by t.team_joiner desc", nativeQuery = true)
	Page<Team> notIncludList(String keyword, Pageable pageable);

	//필터1 예치금/모집인원
	@Query(value = "select * from teams t where t.team_category1= ?1 and team_deposit between '1' and ?2 and team_volume between '2' and ?3 order by t.team_joiner desc", nativeQuery = true)
	Page<Team> depositVolumeList(String keyword, String deposit, String volume, Pageable pageable);
	//필터1 예치금/미포함
	@Query(value = "select * from teams t where t.team_category1= ?1 and team_deposit between '1' and ?2 and t.start_date >= date(now()) order by t.team_joiner desc", nativeQuery = true)
	Page<Team> depositNotIncludList(String keyword, String deposit, Pageable pageable);
	//필터1 모집인원/미포함
	@Query(value = "select * from teams t where t.team_category1= ?1 and team_volume between '2' and ?2 and t.start_date >= date(now()) order by t.team_joiner desc", nativeQuery = true)
	Page<Team> volumeNotIncludList(String keyword, String volume, Pageable pageable);
	//필터1 예치금/모집인원/미포함
	@Query(value = "select * from teams t where t.team_category1= ?1 and team_deposit between '1' and ?2 and team_volume between '2' and ?3 and t.start_date >= date(now()) order by t.team_joiner desc", nativeQuery = true)
	Page<Team> filter1AllList(String keyword, String deposit, String volume, Pageable pageable);
	
	//필터1 예치금/모집인원, 필터2 최신순
	@Query(value = "select * from teams t where t.team_category1= ?1 and team_deposit between '1' and ?2 and team_volume between '2' and ?3 order by t.team_no desc", nativeQuery = true)
	Page<Team> depositVolumeNewList(String keyword, String deposit, String volume, Pageable pageable);
	//필터1 예치금/미포함, 필터2 최신순
	@Query(value = "select * from teams t where t.team_category1= ?1 and team_deposit between '1' and ?2 and t.start_date >= date(now()) order by t.team_no desc", nativeQuery = true)
	Page<Team> depositNotIncludNewList(String keyword, String deposit, Pageable pageable);
	//필터1 모집인원/미포함, 필터2 최신순
	@Query(value = "select * from teams t where t.team_category1= ?1 and team_volume between '2' and ?2 and t.start_date >= date(now()) order by t.team_no desc", nativeQuery = true)
	Page<Team> volumeNotIncludNewList(String keyword, String volume, Pageable pageable);
	//필터1 예치금/모집인원/미포함, 필터2 최신순
	@Query(value = "select * from teams t where t.team_category1= ?1 and team_deposit between '1' and ?2 and team_volume between '2' and ?3 and t.start_date >= date(now()) order by t.team_no desc", nativeQuery = true)
	Page<Team> filter1AllNewList(String keyword, String deposit, String volume, Pageable pageable);
	
	//필터1 예치금/모집인원, 필터2 팀원수순
	@Query(value = "select * from teams t where t.team_category1= ?1 and team_deposit between '1' and ?2 and team_volume between '2' and ?3 order by t.team_joiner desc", nativeQuery = true)
	Page<Team> depositVolumeJoinerList(String keyword, String deposit, String volume, Pageable pageable);
	//필터1 예치금/미포함, 필터2 팀원수순
	@Query(value = "select * from teams t where t.team_category1= ?1 and team_deposit between '1' and ?2 and t.start_date >= date(now()) order by t.team_joiner desc", nativeQuery = true)
	Page<Team> depositNotIncludJoinerList(String keyword, String deposit, Pageable pageable);
	//필터1 모집인원/미포함, 필터2 팀원수순
	@Query(value = "select * from teams t where t.team_category1= ?1 and team_volume between '2' and ?2 and t.start_date >= date(now()) order by t.team_joiner desc", nativeQuery = true)
	Page<Team> volumeNotIncludJoinerList(String keyword, String volume, Pageable pageable);
	//필터1 예치금/모집인원/미포함, 필터2 팀원수순
	@Query(value = "select * from teams t where t.team_category1= ?1 and team_deposit between '1' and ?2 and team_volume between '2' and ?3 and t.start_date >= date(now()) order by t.team_joiner desc", nativeQuery = true)
	Page<Team> filter1AllJoinerList(String keyword, String deposit, String volume, Pageable pageable);
}
