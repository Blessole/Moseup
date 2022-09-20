package project.moseup.dto;

import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.domain.TeamMember;

@Getter @Setter
public class TeamMemberReqDto {
	
	private Member member;
	private Team team;
	
	//팀멤버 생성
	public TeamMember teamMemberBuilder() {
		return TeamMember.createTeamMemberBuilder()
		.member(member)
		.team(team)
		.teamMemberDelete(DeleteStatus.FALSE)
		.build();
	}

	// 팀 멤버 삭제
	public TeamMember teamMemberDelete(){
		return TeamMember.deleteTeamMember()
				.member(member)
				.teamMemberDelete(DeleteStatus.TRUE)
				.build();
	}
}
