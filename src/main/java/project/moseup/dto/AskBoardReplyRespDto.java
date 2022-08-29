package project.moseup.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.moseup.domain.AskBoard;
import project.moseup.domain.AskBoardReply;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AskBoardReplyRespDto {

    private Long arno;
    private AskBoard askBoard;
    private Member member;
    private String askReplyContent;
    private LocalDateTime askReplyDate;
    private DeleteStatus askReplyDelete;

    public AskBoardReplyRespDto toDto(AskBoardReply askBoardReply){
        this.arno = askBoardReply.getArno();
        this.member = askBoardReply.getMember();
        this.askBoard = askBoardReply.getAskBoard();
        this.askReplyContent = askBoardReply.getAskReplyContent();
        this.askReplyDate = askBoardReply.getAskReplyDate();
        this.askReplyDelete = askBoardReply.getAskReplyDelete();
        return this;
    }
}
