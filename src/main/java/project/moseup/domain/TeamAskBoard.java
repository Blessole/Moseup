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
	@NotEmpty
	private LocalDateTime teamAskDate;

	@Column(name = "team_readcount")
	@NotEmpty
	private int teamReadCount;

	@Column(name = "secret")
	@Enumerated(EnumType.STRING)
	@NotEmpty
	private DeleteStatus secret;

	@Column(name = "team_delete")
	@Enumerated(EnumType.STRING)
	@NotEmpty
	private DeleteStatus tabdelete;
	
	@OneToMany(mappedBy = "teamAskBoard")
	private List<TeamAskBoardReply> teamaskboardreply = new ArrayList<>();


}
