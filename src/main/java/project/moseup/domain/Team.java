package project.moseup.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
	
	@NotEmpty
	@Column(name = "team_category1")
	private String  teamCategory1;
	
	@Column(name = "team_category2")
	private String  teamCategory2;
	
	@Column(name = "team_category3")
	private String  teamCategory3;

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
