package project.moseup.dto.teamPage;

import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class TeamDetailDto {

	private Long tno;
	private Member member; // 팀장
	private String teamName; // 팀명
	private int teamVolume;
	private int teamDeposit;
	private String  teamCategory1;	//대분류
	private String  teamCategory2;	//중분류
	private String  teamCategory3;	//소분류
	private LocalDate teamDate; // 팀 생성일
	private LocalDate startDate; // 습관 시작일
	private LocalDate endDate; //습관 종료일
	private String teamLeader;	//*검색용* 팀장 닉네임
	private String teamIntroduce; // 팀 소개글
	private String teamPhoto; // 팀 소개 사진
	private DeleteStatus teamDelete; //팀 삭제여부
	private List<Likes> likes = new ArrayList<>(); // 스터디 좋아요
	private List<TeamMember> teamMembers = new ArrayList<>(); // 팀에 가입된 멤버
	private List<CheckBoard> checkBoards = new ArrayList<>();
	private List<TeamAskBoard> teamAskBoards = new ArrayList<>();

	public TeamDetailDto toDto(Team team) {
		this.tno = team.getTno();
		this.member = team.getMember();
		this.teamName = team.getTeamName();
		this.teamVolume = team.getTeamVolume();
		this.teamDeposit = team.getTeamDeposit();
		this.teamCategory1 = team.getTeamCategory1();
		this.teamCategory2 = team.getTeamCategory2();
		this.teamCategory3 = team.getTeamCategory3();
		this.teamDate = team.getTeamDate();
		this.startDate = team.getStartDate();
		this.endDate = team.getEndDate();
		this.teamLeader = team.getTeamLeader();
		this.teamIntroduce = team.getTeamIntroduce();
		this.teamPhoto = team.getTeamPhoto();
		this.teamDelete = team.getTeamDelete();
		this.likes = team.getLikes();
		this.checkBoards = team.getCheckBoards();
		this.teamAskBoards = team.getTeamAskBoards();
		this.teamMembers = team.getTeamMembers();

		return this;		
	}

	public String getPhotoViewPath(){
		int index = this.teamPhoto.indexOf("images");
		return this.teamPhoto.substring(index - 1);
	}
}
