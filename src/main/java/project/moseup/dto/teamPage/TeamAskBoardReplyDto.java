package project.moseup.dto.teamPage;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.TeamAskBoard;
import project.moseup.domain.TeamAskBoardReply;

@Getter @Setter
public class TeamAskBoardReplyDto  {
	
    private Member member;

    private TeamAskBoard teamAskBoard;

    private String teamAskReplyContent;

    private LocalDateTime teamAskReplyDate;
    
    private DeleteStatus teamAskReplyDelete;
    
    // 댓글 생성 method
    public TeamAskBoardReply toEntity() {
    	return TeamAskBoardReply.createTeamAskBoardReply()
    			.member(member)
    			.teamAskReplyContent(teamAskReplyContent)
    			.teamAskReplyDate(LocalDateTime.now())
    			.teamAskBoard(teamAskBoard)
    			.teamAskReplyDelete(DeleteStatus.FALSE).build();
    }
    
}
