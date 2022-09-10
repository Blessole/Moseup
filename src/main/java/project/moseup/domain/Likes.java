package project.moseup.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@SuppressWarnings("serial")
public class Likes {

	@MapsId("mno")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_no")
	private Member member;

	@MapsId("tno")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_no")
	private Team team;

	@EmbeddedId
	private LikesId likesId = new LikesId();


	@Builder
	public Likes(Member member, Team team) {
		Assert.hasText(String.valueOf(member), "멤버는 [NULL]이 될 수 없습니다");
		Assert.hasText(String.valueOf(team), "멤버는 [NULL]이 될 수 없습니다");

		this.member = member;
		this.team = team;
	}
}
