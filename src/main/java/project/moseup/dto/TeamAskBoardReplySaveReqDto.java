package project.moseup.dto;

import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.TeamAskBoard;
import project.moseup.domain.TeamAskBoardReply;

import java.time.LocalDateTime;

@Getter
@Setter
public class TeamAskBoardReplySaveReqDto {
    Long mno;
    Long tano;
    String reply;

    Member member;
    TeamAskBoard teamAskBoard;

    public TeamAskBoardReply toEntity(){
        return TeamAskBoardReply.createTeamAskBoardReply()
                .teamAskBoard(teamAskBoard)
                .member(member)
                .teamAskReplyContent(reply)
                .teamAskReplyDate(LocalDateTime.now())
                .teamAskReplyDelete(DeleteStatus.FALSE)
                .build();
    }

}
