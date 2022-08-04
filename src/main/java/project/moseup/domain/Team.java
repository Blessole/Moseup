package project.moseup.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "teams")
@NoArgsConstructor
public class Team {

	@Id @GeneratedValue
	@Column(name = "team_no") // 팀 번호
	private Long tno;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_no")
	private Member member; // 팀장

	@NotEmpty
	@Column(name = "team_name")
	private String teamName; // 팀명

	@NotNull
	@Column(name = "team_volume") //팀 모집 인원
	private int teamVolume;

	@NotNull
	@Column(name = "team_deposit") //예치금
	private int teamDeposit;
	
	@NotEmpty
	@Column(name = "team_category1")
	private String  teamCategory1;
	
	@Column(name = "team_category2")
	private String  teamCategory2;
	
	@Column(name = "team_category3")
	private String  teamCategory3;

	private LocalDate teamDate; //팀 생성일

	private LocalDate startDate; //습관 시작일

	private LocalDate endDate; //습관 종료일

	@Builder
	public Team(Member member, String teamName, int teamVolume, int teamDeposit, String teamCategory1, String teamCategory2, String teamCategory3, LocalDate teamDate, LocalDate startDate, LocalDate endDate, String teamIntroduce, String teamPhoto, DeleteStatus teamDelete) {
		this.member = member;
		this.teamName = teamName;
		this.teamVolume = teamVolume;
		this.teamDeposit = teamDeposit;
		this.teamCategory1 = teamCategory1;
		this.teamCategory2 = teamCategory2;
		this.teamCategory3 = teamCategory3;
		this.teamDate = teamDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.teamIntroduce = teamIntroduce;
		this.teamPhoto = teamPhoto;
		this.teamDelete = teamDelete;
	}

	@NotEmpty
	@Column(name = "team_Introduce")
	private String teamIntroduce;  // 팀 소개글

	@Column(name = "team_photo")
	private String teamPhoto; // 팀 소개 사진

	@Enumerated(EnumType.STRING)
	private DeleteStatus teamDelete; //팀 삭제여부
	
	@OneToMany(mappedBy = "team")
    private List<Likes> likes = new ArrayList<>(); // 스터디 좋아요

	@OneToMany(mappedBy = "team")
    private List<TeamMember> teamMembers = new ArrayList<>(); //팀에 가입된 멤버

	@OneToMany(mappedBy = "team")
    private List<CheckBoard> checkBoards = new ArrayList<>();
}
