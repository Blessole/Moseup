package project.moseup.repository.myPage;

import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.Likes;
import project.moseup.domain.Member;
import project.moseup.domain.Team;

public interface LikeInterfaceRepository extends JpaRepository<Likes, Long> {

    Long deleteAllByTeamAndMember(Team team, Member member);
}
