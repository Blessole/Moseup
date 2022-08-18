package project.moseup.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.Team;

@Getter
@NoArgsConstructor
public class TeamCreateRespDto {

	private Long tno; // 팀번호
	private Member member; // 팀장
	private String teamName; // 팀명
	private int teamVolume; // 팀 모집 인원
	private int teamDeposit; // 예치금
	private String teamCategory1; // 대분류
	private String teamCategory2; // 중분류
	private String teamCategory3; // 소분류
	private LocalDate teamDate; // 팀 생성일
	private LocalDate startDate; // 습관 시작일
	private LocalDate endDate; // 습관 종료일
	private String teamIntroduce; // 팀 소개글
	private String teamPhoto; // 팀 소개 사진
	private DeleteStatus teamDelete; // 팀 삭제여부
	private String teamLeader; // *검색용* 팀장 닉네임

	public TeamCreateRespDto pathDto(Team teamPS) {
		this.tno = teamPS.getTno();
		this.member = teamPS.getMember();
		this.teamName = teamPS.getTeamName();
		this.teamVolume = teamPS.getTeamVolume();
		this.teamDeposit = teamPS.getTeamDeposit();
		this.teamCategory1 = teamPS.getTeamCategory1();
		this.teamCategory2 = teamPS.getTeamCategory2();
		this.teamCategory3 = teamPS.getTeamCategory3();
		this.teamDate = teamPS.getTeamDate();
		this.startDate = teamPS.getStartDate();
		this.endDate = teamPS.getEndDate();
		this.teamIntroduce = teamPS.getTeamIntroduce();
		
		 // 사진 경로 local에서 project용으로 변경
        String photo = teamPS.getTeamPhoto();
        int index = photo.indexOf("images");
        String realPhoto = photo.substring(index-1);
        
		this.teamPhoto = realPhoto;
		this.teamDelete = teamPS.getTeamDelete();
		this.teamLeader = teamPS.getTeamLeader();
		return this;
	}

}
