package project.moseup.dto.teamPage;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.SecretStatus;
import project.moseup.domain.TeamAskBoard;

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
		return TeamAskBoard.creatTeamAskBoard()
				.member(member)
				.teamAskSubject(teamAskSubject)
				.teamAskContent(teamAskContent)
				.teamAskDate(LocalDate.now())
				.teamAskReadCount(0)
				.secret(secret)
				.teamAskDelete(DeleteStatus.FALSE).build();
	}
	
	// 게시글 삭제 method
	/*
	 * public TeamAskBoard Delete() { return TeamAskBoard.teamAskBoardD()
	 * .deleteStatus(DeleteStatus.TRUE).build(); }
	 */
}
