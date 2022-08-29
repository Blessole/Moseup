package project.moseup.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "team_members")
@SuppressWarnings("serial")
public class TeamMember implements Serializable {

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_no")
	private Member member;

	@Id
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
