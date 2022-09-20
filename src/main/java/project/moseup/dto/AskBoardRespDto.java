package project.moseup.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.moseup.domain.AskBoard;
import project.moseup.domain.AskBoardReply;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AskBoardRespDto {
    private Long ano;
    private Member member;
    private String askSubject;
    private String askContent;
    private String askPhoto;
    private LocalDateTime askDate;
    private DeleteStatus askDelete;
    private List<AskBoardReply> askBoardReplies;

    public AskBoardRespDto toDto(AskBoard askBoard){
        this.ano = askBoard.getAno();
        this.member = askBoard.getMember();
        this.askSubject = askBoard.getAskSubject();
        this.askContent = askBoard.getAskContent();
        this.askPhoto = askBoard.getAskPhoto();
        this.askDate = askBoard.getAskDate();
        this.askDelete = askBoard.getAskDelete();
        this.askBoardReplies = askBoard.getAskBoardReplies();
        return this;
    }
}
