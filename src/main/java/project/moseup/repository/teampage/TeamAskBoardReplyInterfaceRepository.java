package project.moseup.repository.teampage;

import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.TeamAskBoard;
import project.moseup.domain.TeamAskBoardReply;

import java.util.List;

public interface TeamAskBoardReplyInterfaceRepository extends JpaRepository<TeamAskBoardReply, Long> {
    List<TeamAskBoardReply> findByTeamAskBoardOrderByTarnoDesc(TeamAskBoard teamAskBoard);
}
