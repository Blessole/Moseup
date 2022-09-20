package project.moseup.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.moseup.domain.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class TeamAskBoardRespDto {

    private Long tano;

    private Member member;

    private Team team;

    private String teamAskSubject;

    private String teamAskContent;

    private LocalDate teamAskDate;

    private int teamAskReadCount;

    private SecretStatus secret;

    private DeleteStatus teamAskDelete;

    private List<TeamAskBoardReply> teamAskBoardReplies;

    public TeamAskBoardRespDto toDto(TeamAskBoard teamAskBoard){
        this.tano = teamAskBoard.getTano();
        this.member = teamAskBoard.getMember();
        this.team = teamAskBoard.getTeam();
        this.teamAskSubject = teamAskBoard.getTeamAskSubject();
        this.teamAskContent = teamAskBoard.getTeamAskContent();
        this.teamAskDate = teamAskBoard.getTeamAskDate();
        this.teamAskReadCount = teamAskBoard.getTeamAskReadCount();
        this.secret = teamAskBoard.getSecret();
        this.teamAskDelete = teamAskBoard.getTeamAskDelete();
        this.teamAskBoardReplies = teamAskBoard.getTeamAskBoardReplies();

        return this;
    }
}
