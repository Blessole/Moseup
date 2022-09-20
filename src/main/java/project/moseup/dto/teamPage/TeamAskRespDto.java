package project.moseup.dto.teamPage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.moseup.domain.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class TeamAskRespDto {

    private Long tano;
    private Team team;
    private Member member;
    private String teamAskSubject;
    private String teamAskContent;
    private LocalDate teamAskDate;
    private int teamAskReadCount;
    private SecretStatus secret;
    private DeleteStatus teamAskDelete;

    public TeamAskRespDto toDto(TeamAskBoard teamAskBoardPS){
        this.tano = teamAskBoardPS.getTano();
        this.team = teamAskBoardPS.getTeam();
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
