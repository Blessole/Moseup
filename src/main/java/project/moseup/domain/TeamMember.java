package project.moseup.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "team_members")
@SuppressWarnings("serial")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamMember {

	@MapsId("mno")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_no")
	private Member member;

	@MapsId("tno")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_no")
	private Team team;

	@Enumerated(EnumType.STRING)
	private DeleteStatus teamMemberDelete;

	@EmbeddedId
	private TeamMemberId teamMemberId = new TeamMemberId();

	@Builder(builderClassName = "createTeamMemberBuilder", builderMethodName = "createTeamMemberBuilder")
	public TeamMember(Member member, Team team, DeleteStatus teamMemberDelete) {
		this.member = member;
		this.team = team;
		this.teamMemberDelete = teamMemberDelete;
	}

	// 팀 멤버 삭제용 빌더  - 회원 탈퇴 시 사용
	@Builder(builderClassName = "deleteTeamMember", builderMethodName = "deleteTeamMember")
	public TeamMember(Member member, DeleteStatus teamMemberDelete){
		this.member = member;
		this.teamMemberDelete = teamMemberDelete;
	}

	public void deleteUpdate(DeleteStatus deleteStatus){
		this.teamMemberDelete = deleteStatus;

	}
}
