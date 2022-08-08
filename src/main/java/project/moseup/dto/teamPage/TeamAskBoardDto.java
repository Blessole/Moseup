package project.moseup.dto.teamPage;

import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.SecretStatus;
import project.moseup.domain.TeamAskBoard;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;



@Getter @Setter
public class TeamAskBoardDto {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_no")
	private Member member;

	@Column(name = "team_asksubject")
	@NotEmpty
	private String teamAskSubject;

	@Column(name = "team_askcontent")
	@NotEmpty
	private String teamAskContent;

	@Column(name = "team_askdate")
	private LocalDate teamAskDate;

	@Column(name = "team_askreadcount")
	private int teamAskReadCount;

	@Column(name = "team_asksecret")
	@Enumerated(EnumType.STRING)
	private SecretStatus secret;

	@Column(name = "team_askdelete")
	@Enumerated(EnumType.STRING)
	private DeleteStatus teamAskDelete;

	// 게시글 생성 method
	public TeamAskBoard toEntity() {
		return TeamAskBoard.teamAskBoard()
				.member(member)
				.teamAskSubject(teamAskSubject)
				.teamAskContent(teamAskContent)
				.teamAskDate(LocalDate.now())
				.teamAskReadCount(0)
				.secret(SecretStatus.PUBLIC)
				.teamAskDelete(DeleteStatus.FALSE).build();
	}

	// 게시글 삭제 method
	public static TeamAskBoard toDelete() {
		return TeamAskBoard.teamAskBoardDelete()

				.deleteStatus(DeleteStatus.TRUE)
				.build();
	}
}
