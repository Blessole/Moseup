package project.moseup.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Getter
@Table(name = "team_members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamMember implements Serializable {

	@EmbeddedId
	private TeamMemberId teamMemberId = new TeamMemberId();

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
	
	@Builder(builderClassName = "createTeamMemberBuilder", builderMethodName = "createTeamMemberBuilder")
	public TeamMember(Member member, Team team, DeleteStatus teamMemberDelete) {
		this.member = member;
		this.team = team;
		this.teamMemberDelete = teamMemberDelete;
	}

}
