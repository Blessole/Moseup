package project.moseup.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 쉽게 접근할 수 없게!
public class TeamAskBoard {

	@Column(name = "team_askno")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long tano;

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
	
	@Builder(builderClassName = "toEntity", builderMethodName = "creatTeamAskBoard")
	public TeamAskBoard(Member member, String teamAskSubject, String teamAskContent, LocalDate teamAskDate, int teamAskReadCount, SecretStatus secret, DeleteStatus teamAskDelete) {
		this.member = member;
		this.teamAskSubject = teamAskSubject;
		this.teamAskContent = teamAskContent;
		this.teamAskDate = teamAskDate;
		this.teamAskDelete = teamAskDelete;
		this.teamAskReadCount = teamAskReadCount;
		this.secret = secret;
	}
	
	// 문의 글 수정 로직
	public void changeBoardContent(String teamAskSubject, String teamAskContent, SecretStatus secret) {
		this.teamAskSubject = teamAskSubject;
		this.teamAskContent = teamAskContent;
		this.secret = secret;
	}
	
	// 문의 글 삭제 로직
	public void changeBoardDelete(DeleteStatus teamAskDelete) {
		this.teamAskDelete = teamAskDelete;
	}
	
	// 조회수 증가 로직
	public void increaseReadCount(int teamAskReadCount) {
		this.teamAskReadCount = teamAskReadCount;
	}

	@OneToMany(mappedBy = "teamAskBoard")
	private List<TeamAskBoardReply> teamAskBoardReplies = new ArrayList<>();

}
