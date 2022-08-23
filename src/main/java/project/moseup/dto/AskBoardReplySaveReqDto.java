package project.moseup.dto;

import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.AskBoard;
import project.moseup.domain.AskBoardReply;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
public class AskBoardReplySaveReqDto {

    @NotEmpty(message = "문의글 정보가 없습니다.")
    private AskBoard askBoard;

    @NotEmpty(message = "작성자 정보가 없습니다")
    private Member member;

    @NotEmpty(message = "내용을 입력해주세요.")
    private String askReplyContent;

    public AskBoardReply toEntity() {
        return AskBoardReply.builder()
                .askBoard(askBoard)
                .askReplyContent(askReplyContent)
                .askReplyDate(LocalDateTime.now())
                .askReplyDelete(DeleteStatus.FALSE)
                .member(member)
                .build();
    }

}


