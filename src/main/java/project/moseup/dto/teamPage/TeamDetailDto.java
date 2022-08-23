package project.moseup.dto.teamPage;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.Team;
import project.moseup.domain.TeamAskBoard;

@Getter @Setter
public class TeamDetailDto {
	
	private Long tno;

	private List<TeamAskBoard> teamAskBoards;
	
	public TeamDetailDto toDto(Team team) {
		this.tno = team.getTno();
		this.teamAskBoards = team.getTeamAskBoards();
		return this;		
	}
}
