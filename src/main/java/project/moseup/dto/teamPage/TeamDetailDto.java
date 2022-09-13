package project.moseup.dto.teamPage;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.Team;
import project.moseup.domain.TeamAskBoard;
import project.moseup.domain.TeamMember;

@Getter @Setter
public class TeamDetailDto {
	
	private Long tno;
	
	private String teamName;
	
	private String teamLeader; 

	private List<TeamAskBoard> teamAskBoards;
	
	private List<TeamMember> teamMember;
	
	public TeamDetailDto toDto(Team team) {
		this.tno = team.getTno();
		this.teamName = team.getTeamName();
		this.teamLeader = team.getTeamLeader();
		this.teamAskBoards = team.getTeamAskBoards();
		this.teamMember = team.getTeamMembers();
		return this;		
	}
}
