package project.moseup.dto.teamPage;

import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.domain.TeamMember;

@Getter @Setter
public class TeamMemberDto {
	
	private Member member;
	
	private Team team;
	
	private DeleteStatus teamMemberDelete;
	
	// 팀 가입 method
	public TeamMember toEntity() {
		return TeamMember.createTeamMemberBuilder()
				.member(member)
				.team(team)
				.teamMemberDelete(DeleteStatus.FALSE).build();
	}
}
