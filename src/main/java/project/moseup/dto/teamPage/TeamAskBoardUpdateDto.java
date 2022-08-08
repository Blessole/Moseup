package project.moseup.dto.teamPage;

import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.SecretStatus;
import project.moseup.domain.TeamAskBoard;

@Getter @Setter
public class TeamAskBoardUpdateDto {
	
	private Long tano;

	private String teamAskSubject;

	private String teamAskContent;

	private SecretStatus secret;
	
	public TeamAskBoard update() {
		return TeamAskBoard.updateTeamAskBoard()
				.tano(tano)
				.teamAskSubject(teamAskSubject)
				.teamAskContent(teamAskContent)
				.secret(secret).build();
	}

}
