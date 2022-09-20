package project.moseup.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.moseup.domain.AskBoard;
import project.moseup.domain.AskBoardReply;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter @Setter
@ToString
@NoArgsConstructor
public class AskBoardReplySaveReqDto {

    private AskBoard askBoard;

    private Member member;

    @NotEmpty(message = "내용을 입력해주세요.")
    private String askReplyContent;

    private LocalDateTime askReplyDate;

    private DeleteStatus askReplyDelete;

    public AskBoardReply toEntity(){
        return AskBoardReply.builder()
                .askBoard(askBoard)
                .member(member)
                .askReplyContent(askReplyContent)
                .askReplyDate(LocalDateTime.now())
                .askReplyDelete(DeleteStatus.FALSE)
                .build();
    }
}
