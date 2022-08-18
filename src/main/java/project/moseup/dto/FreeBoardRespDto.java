package project.moseup.dto;

import lombok.Getter;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.FreeBoard;
import project.moseup.domain.FreeBoardReply;
import project.moseup.domain.Member;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class FreeBoardRespDto {

    private Long fno;				//게시물 번호
    private Member member;				//회원 번호
    private String freeTitle;			//게시물 제목
    private String freeContent;		//게시물 내용
    private int freeLike;				//좋아요
    private String freeStrDate;
    private int freeReadCount;				//조회수
    private DeleteStatus freeDelete;	//게시물 삭제 여부
    private List<FreeBoardReply> freeBoardReplies = new ArrayList<>();



    public FreeBoardRespDto toDto(FreeBoard freeBoard){
        this.fno = freeBoard.getFno();
        this.member = freeBoard.getMember();
        this.freeTitle = freeBoard.getFreeTitle();
        this.freeContent = freeBoard.getFreeContent();
        this.freeLike = freeBoard.getFreeLike();
        this.freeStrDate = freeBoard.getFreeDate().format(DateTimeFormatter.ISO_DATE);
        this.freeReadCount = freeBoard.getFreeReadCount();
        this.freeDelete = freeBoard.getFreeDelete();
        this.freeBoardReplies = freeBoard.getFreeBoardReplies();
        return this;
    }
}
