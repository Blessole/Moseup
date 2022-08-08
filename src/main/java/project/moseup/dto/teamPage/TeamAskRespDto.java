package project.moseup.dto.teamPage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.SecretStatus;
import project.moseup.domain.TeamAskBoard;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class TeamAskRespDto {

    private Long tano;
    private Member member;
    private String teamAskSubject;
    private String teamAskContent;
    private LocalDate teamAskDate;
    private int teamAskReadCount;
    private SecretStatus secret;
    private DeleteStatus teamAskDelete;

    public TeamAskRespDto toDto(TeamAskBoard teamAskBoardPS){
        this.tano = teamAskBoardPS.getTano();
        this.member = teamAskBoardPS.getMember();
        this.teamAskSubject = teamAskBoardPS.getTeamAskSubject();
        this.teamAskContent = teamAskBoardPS.getTeamAskContent();
        this.teamAskDate = teamAskBoardPS.getTeamAskDate();
        this.teamAskReadCount = teamAskBoardPS.getTeamAskReadCount();
        this.secret = teamAskBoardPS.getSecret();
        this.teamAskDelete = teamAskBoardPS.getTeamAskDelete();
        return this;
    }

}
