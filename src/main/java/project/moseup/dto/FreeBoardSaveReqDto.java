package project.moseup.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.FreeBoard;
import project.moseup.domain.Member;

import java.time.LocalDateTime;

@Getter
@Setter
public class FreeBoardSaveReqDto {

    private Member member;				//회원 번호
    private String freeTitle;			//게시물 제목
    private String freeContent;		//게시물 내용

    @Builder
    public FreeBoardSaveReqDto(Member member, String freeTitle, String freeContent) {
        this.member = member;
        this.freeTitle = freeTitle;
        this.freeContent = freeContent;
    }

    public FreeBoard toEntity(){
        return FreeBoard.builder()
                .member(member)
                .freeTitle(freeTitle)
                .freeContent(freeContent)
                .freeDate(LocalDateTime.now())
                .freeLike(0)
                .freeDelete(DeleteStatus.FALSE)
                .freeReadCount(0)
                .build();
    }
}
