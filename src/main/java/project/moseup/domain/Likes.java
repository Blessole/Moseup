package project.moseup.domain;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@IdClass(LikesId.class)
@SuppressWarnings("serial")
public class Likes {

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_no")
	private Member member;


	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_no")
	private Team team;

	public Likes(){}

	@Builder
	public Likes(Member member, Team team) {
		this.member = member;
		this.team = team;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Likes likes = (Likes) o;
		return member != null && Objects.equals(member, likes.member)
				&& team != null && Objects.equals(team, likes.team);
	}

	@Override
	public int hashCode() {
		return Objects.hash(member, team);
	}
}
