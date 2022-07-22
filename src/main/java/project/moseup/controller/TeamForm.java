package project.moseup.controller;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;

@Getter @Setter
public class TeamForm {
	private Member member;					//팀장
	private String teamName;					//팀명
	private int teamVolume;						//모집 인원
	private int teamDeposit;						//예치금
	private LocalDateTime teamDate;		//팀 생성일
	private LocalDateTime startDate;			//습관 시작일
	private LocalDateTime endDate;			//습관 종료일
	private String teamIntroduce;				//팀 소개 글
	private String teamPhoto;					//팀 소개 사진
	private DeleteStatus teamDelete;		//팀 삭제 여부
}
