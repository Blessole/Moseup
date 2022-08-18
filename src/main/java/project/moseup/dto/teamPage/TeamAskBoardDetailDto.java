package project.moseup.dto.teamPage;

import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.*;

import java.time.LocalDate;

@Getter @Setter
public class TeamAskBoardDetailDto {
	
	private Long tano;

	private Team team;

	private Member member;

	private String teamAskSubject;

	private String teamAskContent;

	private LocalDate teamAskDate;

	private int teamAskReadCount;

	private SecretStatus secret;

	private DeleteStatus teamAskDelete;
	
	public TeamAskBoardDetailDto toDto(TeamAskBoard teamAskBoard) {
		this.tano = teamAskBoard.getTano();
		this.team = teamAskBoard.getTeam();
		this.member = teamAskBoard.getMember();
		this.teamAskSubject = teamAskBoard.getTeamAskSubject();
		this.teamAskContent = teamAskBoard.getTeamAskContent();
		this.teamAskDate = teamAskBoard.getTeamAskDate();
		this.teamAskReadCount = teamAskBoard.getTeamAskReadCount();
		this.secret = teamAskBoard.getSecret();
		this.teamAskDelete = teamAskBoard.getTeamAskDelete();
		return this;
	}

}
