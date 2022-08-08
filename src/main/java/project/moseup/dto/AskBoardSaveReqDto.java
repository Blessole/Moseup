package project.moseup.dto;

import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.AskBoard;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter @Setter
public class AskBoardSaveReqDto {

    private Member member;

    @NotEmpty(message = "제목을 입력해주세요.")
    private String askSubject;

    @NotEmpty(message = "내용을 입력해주세요.")
    private String askContent;

    private String askPhoto;

    public AskBoard toEntity(){
        return AskBoard.builder()
                .member(member)
                .askSubject(askSubject)
                .askContent(askContent)
                .askDate(LocalDateTime.now())
                .askDelete(DeleteStatus.FALSE)
                .askPhoto(askPhoto)
                .build();
    }
}
