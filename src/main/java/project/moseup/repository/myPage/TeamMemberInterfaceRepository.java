package project.moseup.repository.myPage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.domain.TeamMember;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TeamMemberInterfaceRepository extends JpaRepository<TeamMember, Long> {

    String findByMemberAndStartDateAfter = "select tm from TeamMember tm where tm.member = :member and tm.team.tno in (select t.tno from Team t where t.startDate > :localDate)";
    @Query(findByMemberAndStartDateAfter)
    List<TeamMember> findByMemberAndStartDateAfter(@Param("member") Member member, @Param("localDate") LocalDate localDate);

    Optional<TeamMember> findByTeamAndMember(Team team, Member member);
//    Optional<TeamMember> findByMember(Optional<Member> member);
}
