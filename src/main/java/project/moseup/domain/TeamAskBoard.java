package project.moseup.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
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
	private LocalDateTime teamAskDate;

	@Column(name = "team_askreadcount")
	private int teamAskReadCount;

	@Column(name = "team_asksecret")
	@Enumerated(EnumType.STRING)
	private DeleteStatus secret;

	@Column(name = "team_askdelete")
	@Enumerated(EnumType.STRING)
	private DeleteStatus teamAskDelete;

	@OneToMany(mappedBy = "teamAskBoard")
	private List<TeamAskBoardReply> teamAskBoardReplies = new ArrayList<>();

}
