package project.moseup.dto;

import lombok.*;
import project.moseup.domain.AskBoard;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter @Setter
@ToString
@NoArgsConstructor
public class AskBoardSaveReqDto {

    private Member member;

    @NotEmpty(message = "제목을 입력해주세요.")
    private String askSubject;

    @NotEmpty(message = "내용을 입력해주세요.")
    private String askContent;

    private String askPhoto;

    private LocalDateTime askDate;
    private DeleteStatus askDelete;

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

    @Builder
    public AskBoardSaveReqDto(Member member, String askSubject, String askContent, String askPhoto, LocalDateTime askDate, DeleteStatus askDelete) {
        this.member = member;
        this.askSubject = askSubject;
        this.askContent = askContent;
        this.askPhoto = askPhoto;
        this.askDate = askDate;
        this.askDelete = askDelete;
    }
}
