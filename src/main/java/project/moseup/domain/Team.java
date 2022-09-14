package project.moseup.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//생성자를 따로 안 만들면 자동으로 기본 생성자가 생성됨 하지만 다른 생성자가 있으면 기본 생성자를 만들어 줘야 함
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "teams")
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "team_no") // 팀 번호
	private Long tno;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_no")
	private Member member; // 팀장

	@NotEmpty
	@Column(name = "team_name")
	private String teamName; // 팀명

	@NotNull
	@Column(name = "team_volume") // 팀 모집 인원
	private int teamVolume;

	@NotNull
	@Column(name = "team_deposit") // 예치금
	private int teamDeposit;

	@NotEmpty
	@Column(name = "team_category1")
	private String  teamCategory1;	//대분류
	

	@Column(name = "team_category2")
	private String  teamCategory2;	//중분류
	

	@Column(name = "team_category3")
	private String  teamCategory3;	//소분류

	@CreatedDate
	private LocalDate teamDate; // 팀 생성일

	private LocalDate startDate; // 습관 시작일

	private LocalDate endDate; //습관 종료일
	
	private String teamLeader;	//*검색용* 팀장 닉네임
	
	@NotEmpty
	@Column(name = "team_Introduce")
	private String teamIntroduce; // 팀 소개글

	@Column(name = "team_photo")
	private String teamPhoto; // 팀 소개 사진

	@Enumerated(EnumType.STRING)
	private DeleteStatus teamDelete; //팀 삭제여부
	
	// 연관관계 맵핑
	@OneToOne(mappedBy = "team", cascade = CascadeType.ALL)
	private TeamBankbook teamBankbook;

	@OneToMany(mappedBy = "team")
	private List<Likes> likes = new ArrayList<>(); // 스터디 좋아요

	@OneToMany(mappedBy = "team")
	private List<TeamMember> teamMembers = new ArrayList<>(); // 팀에 가입된 멤버

	@OneToMany(mappedBy = "team")
    private List<CheckBoard> checkBoards = new ArrayList<>();
	
	@OneToMany(mappedBy = "team")
	private List<TeamAskBoard> teamAskBoards = new ArrayList<>();


	@Builder(builderClassName = "createTeamBuilder", builderMethodName = "createTeamBuilder") //빌더 어노테이션을 명시하면 생성자에 독립적으로 사용 가능함 원하는 값만 넣을 수 있고 순서가 중요하지 않음 setter X
	public Team(Member member, String teamName, int teamVolume, int teamDeposit, String teamCategory1, String teamCategory2, String teamCategory3, LocalDate teamDate, LocalDate startDate, LocalDate endDate, String teamIntroduce, String teamPhoto, DeleteStatus teamDelete, String teamLeader) {
		// 안전한 객체 생성 패턴 = 필요한 값이 없는 경우에 NULL 예외가 발생해 메시지를 보여주고 흐름 종료
		Assert.hasText(teamName, "teamName은 [NULL]이 될 수 없습니다");
		Assert.hasText(String.valueOf(teamVolume), "teamVolume은 [NULL]이 될 수 없습니다");
		Assert.hasText(String.valueOf(teamDeposit), "teamDeposit은 [NULL]이 될 수 없습니다");
		Assert.hasText(teamCategory1, "teamCategory1은 [NULL]이 될 수 없습니다");
		Assert.hasText(String.valueOf(teamDate), "teamDate는 [NULL]이 될 수 없습니다");
		Assert.hasText(String.valueOf(startDate), "startDate는 [NULL]이 될 수 없습니다");
		Assert.hasText(String.valueOf(endDate), "endDate는 [NULL]이 될 수 없습니다");
		Assert.hasText(teamIntroduce, "teamIntroduce는 [NULL]이 될 수 없습니다");
		
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
		this.teamLeader = teamLeader;
	}
	
	@Builder(builderClassName = "deleteTeamBuilder", builderMethodName = "deleteTeamBuilder")
	public Team(DeleteStatus teamDelete) {
		
		this.teamDelete = teamDelete;
	}

	public String getPhotoViewPath(){
		int index = this.teamPhoto.indexOf("images");
		return this.teamPhoto.substring(index - 1);
	}
	
	//정보 수정
	public void updateMember(Member member) {
		this.member = member;
	}
	public void updateTeamName(String teamName) {
		this.teamName = teamName;
	}
	public void updateTeamVolume(int teamVolume) {
		this.teamVolume = teamVolume;
	}
	public void updateTeamDeposit(int teamDeposit) {
		this.teamDeposit = teamDeposit;
	}
	public void updateTeamCategory1(String teamCategory1) {
		this.teamCategory1 = teamCategory1;
	}
	public void updateTeamCategory2(String teamCategory2) {
		this.teamCategory2 = teamCategory2;
	}
	public void updateTeamCategory3(String teamCategory3) {
		this.teamCategory3 = teamCategory3;
	}
	public void updateTeamDate(LocalDate teamDate) {
		this.teamDate = teamDate;
	}
	public void updateStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public void updateTeamIntroduce(String teamIntroduce) {
		this.teamIntroduce = teamIntroduce;
	}
	public void updateTeamPhoto(String teamPhoto) {
		this.teamPhoto = teamPhoto;
	}
	public void updateeamDelete(DeleteStatus teamDelete) {
		this.teamDelete = teamDelete;
	}

}
