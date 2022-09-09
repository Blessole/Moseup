package project.moseup.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
public class Likes {

	@EmbeddedId
	private LikesId likesId = new LikesId();

	@MapsId("mno")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_no")
	private Member member;

	@MapsId("tno")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_no")
	private Team team;

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
