package project.moseup.dto;

import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.FreeBoard;
import project.moseup.domain.FreeBoardReply;
import project.moseup.domain.Member;

import java.time.LocalDateTime;

@Setter
@Getter
public class FreeBoardReplySaveDto {
    Long fno;       //게시글 ID
    Long mno;       //댓글 작성자 ID
    String reply;   // 댓글 내용


    FreeBoard freeBoard;
    Member member;

    public FreeBoardReply toEntity(){
        return FreeBoardReply.builder()
                .freeBoard(freeBoard)
                .member(member)
                .freeReplyDelete(DeleteStatus.FALSE)
                .freeReplyDate(LocalDateTime.now())
                .freeReplyContent(reply)
                .level(1)
                .step(1)
                .build();
    }
}



