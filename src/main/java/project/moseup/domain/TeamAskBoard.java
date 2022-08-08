package project.moseup.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 쉽게 접근할 수 없게!
public class TeamAskBoard {

	@Column(name = "team_askno")
	@GeneratedValue @Id
	private Long tano;

	@ManyToOne(fetch = FetchType.EAGER)
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


	// 문의글 수정 메소드
	public void subjectAndContentAndSecretUpdate(String teamAskSubject, String teamAskContent, SecretStatus secret){
		this.teamAskContent = teamAskContent;
		this.teamAskSubject = teamAskSubject;
		this.secret = secret;
	}
	
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
	
	@Builder(builderClassName = "update", builderMethodName = "updateTeamAskBoard")
	public TeamAskBoard(Long tano, String teamAskSubject, String teamAskContent, SecretStatus secret) {
		this.tano = tano;
		this.teamAskSubject = teamAskSubject;
		this.teamAskContent = teamAskContent;
		this.secret = secret;
	}
	
	/*
	 * @Builder(builderClassName = "Delete", builderMethodName = "teamAskBoardD")
	 * public TeamAskBoard(DeleteStatus deleteStatus) { this.teamAskDelete =
	 * deleteStatus; }
	 */
	
	// 게시글 삭제 method
	/*
	 * public void deleteTeamAskBoard() { this.setTeamAskDelete(DeleteStatus.TRUE);
	 * }
	 */

	@OneToMany(mappedBy = "teamAskBoard")
	private List<TeamAskBoardReply> teamAskBoardReplies = new ArrayList<>();

}
