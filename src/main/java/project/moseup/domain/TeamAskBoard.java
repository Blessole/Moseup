package project.moseup.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 쉽게 접근할 수 없게!
public class TeamAskBoard {

	@Column(name = "team_askno")
	@GeneratedValue @Id
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
	
	@Builder(builderClassName = "teamAskBoard", builderMethodName = "teamAskBoard")
	public TeamAskBoard(Member member, String teamAskSubject, String teamAskContent, LocalDate teamAskDate, int teamAskReadCount, SecretStatus secret, DeleteStatus teamAskDelete) {
		this.member = member;
		this.teamAskSubject = teamAskSubject;
		this.teamAskContent = teamAskContent;
		this.teamAskDate = teamAskDate;
		this.teamAskDelete = teamAskDelete;
		this.teamAskReadCount = teamAskReadCount;
		this.secret = secret;
	}

	@Builder(builderClassName = "teamAskBoardDelete", builderMethodName = "teamAskBoardDelete")
	public TeamAskBoard(DeleteStatus deleteStatus) {
		this.teamAskDelete = deleteStatus;
	}

	@OneToMany(mappedBy = "teamAskBoard")
	private List<TeamAskBoardReply> teamAskBoardReplies = new ArrayList<>();

}
