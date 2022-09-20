package project.moseup.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.domain.TeamMember;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class TeamCreateReqDto {
	
	@NotEmpty(message = "팀명은 필수 입력사항입니다!")
	private String teamName;					//팀명
	
	private Member member;					//팀장
	private int teamVolume;						//모집 인원
	private int teamJoiner;						//가입 인원
	private int teamDeposit;						//예치금
	private String teamCategory1;			//카테고리 대분류
	private String teamCategory2;			//카테고리 중분류
	private String teamCategory3;			//카테고리 소분류
	
	@NotNull(message = "습관 시작일은 필수 선택사항입니다!")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(message = "오늘 이전의 날짜는 선택할 수 없습니다!")		//@FutureOrPresent : Now이거나 미래의 날짜, 시간이어야 한다.
	private LocalDate startDate;				//습관 시작일
	
	@NotNull(message = "습관 종료일은 필수 선택사항 입니다!")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(message = "오늘 이전의 날짜는 선택할 수 없습니다!")
	private LocalDate endDate;					//습관 종료일
	
	@NotEmpty(message = "팀 소개 글은 필수 입력사항입니다!")
	private String teamIntroduce;				//팀 소개 글
	
	private String teamPhoto;					//팀 소개 사진
	
	private String teamLeader;					//*검색용* 팀장 닉네임
	
	
	
	private List<TeamMember> teamMembers;	//팀멤버

	private LocalDate teamDate;				//팀 생성일

	private DeleteStatus teamDelete;		//팀 삭제여부


	//팀생성 메서드
	public Team teamBuilder() {
		return Team.createTeamBuilder()
				.member(member)
				.teamName(teamName)
				.teamVolume(teamVolume)
				.teamJoiner(teamJoiner)
				.teamDeposit(teamDeposit)
				.teamCategory1(teamCategory1)
				.teamCategory2(teamCategory2)
				.teamCategory3(teamCategory3)
				.teamDate(LocalDate.now())
				.startDate(startDate)
				.endDate(endDate)
				.teamIntroduce(teamIntroduce)
				.teamPhoto(teamPhoto)
				.teamDelete(DeleteStatus.FALSE)
				.teamLeader(teamLeader)
				.build();
	}
	
	private TeamCreateReqDto toDto(Team team) {
		this.member = team.getMember();
		this.teamName = team.getTeamName();
		this.teamVolume = team.getTeamVolume();
		this.teamJoiner = team.getTeamJoiner();
		this.teamDeposit = team.getTeamDeposit();
		this.teamCategory1 = team.getTeamCategory1();
		this.teamCategory2 = team.getTeamCategory2();
		this.teamCategory3 = team.getTeamCategory3();
		this.teamDate = team.getTeamDate();
		this.startDate = team.getStartDate();
		this.endDate = team.getEndDate();
		this.teamIntroduce = team.getTeamIntroduce();
		this.teamPhoto = team.getTeamPhoto();
		this.teamDelete = team.getTeamDelete();
		this.teamLeader = team.getTeamLeader();
		this.teamMembers = team.getTeamMembers();
		return this;
	}
	
	//팀삭제 메서드
	public Team teamDeleteBuilder() {
		return Team.deleteTeamBuilder()
				.teamDelete(DeleteStatus.TRUE)
				.build();
	}
}
