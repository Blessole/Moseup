package project.moseup.repository.myPage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.CheckBoard;
import project.moseup.domain.Member;
import project.moseup.domain.Team;

import java.util.List;
import java.util.Optional;

public interface CheckBoardInterfaceRepository extends JpaRepository<CheckBoard, Long> {

    List<CheckBoard> findByMember(Member member);

    Page<CheckBoard> findByMember(Member member, Pageable pageable);

    Page<CheckBoard> findByMemberAndTeam(Member member, Team team, Pageable pageable);
}
