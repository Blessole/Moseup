package project.moseup.dto.teamPage;

import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.*;

import java.time.LocalDate;

@Getter @Setter
public class TeamAskBoardDto {

	private Team team;

	private Member member;

	private String teamAskSubject;

	private String teamAskContent;

	private LocalDate teamAskDate;

	private int teamAskReadCount;

	private SecretStatus secret;

	private DeleteStatus teamAskDelete;
	
	// 게시글 생성 method
	public TeamAskBoard toEntity() {
		return TeamAskBoard.creatTeamAskBoard()
				.team(team)
				.member(member)
				.teamAskSubject(teamAskSubject)
				.teamAskContent(teamAskContent)
				.teamAskDate(LocalDate.now())
				.teamAskReadCount(0)
				.secret(secret)
				.teamAskDelete(DeleteStatus.FALSE).build();
	}

}
