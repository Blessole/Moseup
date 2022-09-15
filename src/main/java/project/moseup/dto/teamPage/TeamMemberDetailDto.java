package project.moseup.dto.teamPage;

import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.domain.TeamMember;

public class TeamMemberDetailDto {

	private Member member;
	
	private Team team;
	
	private DeleteStatus teamMemberDelete;
	
	public TeamMemberDetailDto toDto(TeamMember teamMember) {
		this.member = teamMember.getMember();
		this.team = teamMember.getTeam();
		this.teamMemberDelete = teamMember.getTeamMemberDelete();
		return this;
	}
}
