package project.moseup.dto.teamPage;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.SecretStatus;
import project.moseup.domain.TeamAskBoard;
import project.moseup.domain.TeamAskBoardReply;

@Getter @Setter
public class TeamAskBoardDetailDto {
	
	private Long tano;
	
	private Member member;

	private String teamAskSubject;

	private String teamAskContent;

	private LocalDate teamAskDate;

	private int teamAskReadCount;

	private SecretStatus secret;

	private DeleteStatus teamAskDelete;
	
	// 연관관계를 이용해서 보여주기!!
	private List<TeamAskBoardReply> teamAskBoardReplies;
	
	public TeamAskBoardDetailDto toDto(TeamAskBoard teamAskBoard) {
		this.tano = teamAskBoard.getTano();
		this.member = teamAskBoard.getMember();
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
