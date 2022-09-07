package project.moseup.repository.myPage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.moseup.domain.Member;
import project.moseup.domain.Team;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TeamInterfaceRepository extends JpaRepository<Team, Long> {

    String findTeamMember = "select t from Team t where t.tno in ( select tm.team.tno from TeamMember tm where tm.member = :member)";
    String findByMemberAndStartDateAfter = "select t from Team t where t.startDate > :localDate and t.tno in ( select tm.team.tno from TeamMember tm where tm.member = :member)";
    String findByMemberAndEndDateBefore = "select t from Team t where t.endDate < :localDate and t.tno in ( select tm.team.tno from TeamMember tm where tm.member = :member)";
    String findByMemberAndStartDateBeforeAndEndDateAfter = "select t from Team t where t.endDate >= :localDate and t.startDate <= :localDate and t.tno in ( select tm.team.tno from TeamMember tm where tm.member = :member)";
    String findByMemberAndLikes = "select t from Team t where t.tno in (select l.team.tno from Likes l where l.member = :member)";

    /** 마이페이지용 - 가입 팀 조회 **/
    @Query(findTeamMember)
    Page<Team> findTeamMember(@Param("member") Member member, Pageable pageable);

    /** 마이페이지용 - 내가 팀장인 팀 조회 **/
    Page<Team> findByMember(Member member, Pageable pageable);

    /** 마이페이지용 - 진행 예정 팀 조회 **/
    @Query(findByMemberAndStartDateAfter)
    Page<Team> findByMemberAndStartDateAfter(@Param("member") Member member, @Param("localDate") LocalDate localDate, Pageable pageable);

    /** 마이페이지용 - 진행 종료 팀 조회 **/
    @Query(findByMemberAndEndDateBefore)
    Page<Team> findByMemberAndEndDateBefore(@Param("member") Member member, @Param("localDate") LocalDate localDate, Pageable pageable);

    /** 마이페이지용 - 진행 중인 팀 조회 **/
    @Query(findByMemberAndStartDateBeforeAndEndDateAfter)
    Page<Team> findByMemberAndStartDateBeforeAndEndDateAfter(@Param("member") Member member, @Param("localDate") LocalDate localDate, Pageable pageable);

    @Query(findByMemberAndLikes)
    Page<Team> findMyLikeTeam(@Param("member") Member member, Pageable page);
}
