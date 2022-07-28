package project.moseup.domain;

import java.time.LocalDate;

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
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
@Table(name = "teams")
public class Team {

	@Id @GeneratedValue
	@Column(name = "team_no")
	private Long tno;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_no")
	private Member member;

	@NotEmpty
	@Column(name = "team_name")
	private String teamName;

	@NotNull
	@Column(name = "team_volume")
	private int teamVolume;

	@NotNull
	@Column(name = "team_deposit")
	private int teamDeposit;

	private LocalDate teamDate;

	private LocalDate startDate;

	private LocalDate endDate;

	@NotEmpty
	@Column(name = "team_Introduce")
	private String teamIntroduce;

	@Column(name = "team_photo")
	private String teamPhoto;

	@Enumerated(EnumType.STRING)
	private DeleteStatus teamDelete;
	
	@OneToMany(mappedBy = "team")
    private List<Likes> likes = new ArrayList<>();

	@OneToMany(mappedBy = "team")
    private List<TeamMember> teamMembers = new ArrayList<>();

	@OneToMany(mappedBy = "team")
    private List<CheckBoard> checkBoards = new ArrayList<>();
}
