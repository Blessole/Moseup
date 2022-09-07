package project.moseup.dto;

import lombok.Getter;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.FreeBoard;
import project.moseup.domain.FreeBoardReply;
import project.moseup.domain.Member;

import java.time.LocalDateTime;

@Getter
public class FreeBoardReplyRespDto {

    private Long frno;					            //자게 댓글 번호
    private FreeBoard freeBoard;		            //게시물 번호
    private Member member;
    private String freeReplyContent;				//댓글 내용
    private LocalDateTime freeReplyDate;			//댓글 작성일
    private int step;			            		//댓글 순서
    private int level;					            //댓글 깊이
    private DeleteStatus freeReplyDelete;		    //댓글 삭제 여부

    public FreeBoardReplyRespDto toRespDto(FreeBoardReply reply){
        this.frno = reply.getFrno();
        this.freeBoard = reply.getFreeBoard();
        this.member = reply.getMember();
        this.freeReplyContent = reply.getFreeReplyContent();
        this.freeReplyDate = reply.getFreeReplyDate();
        this.step = reply.getStep();
        this.level = reply.getLevel();
        this.freeReplyDelete = reply.getFreeReplyDelete();
        return this;
    }
}



