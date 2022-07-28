package project.moseup.controller;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import ch.qos.logback.core.joran.conditional.IfAction;
import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;

@Getter @Setter
public class TeamForm {
	
	@NotEmpty(message = "팀명은 필수 입력값 입니다. :)")
	private String teamName;					//팀명

	private Member member;					//팀장
	private int teamVolume;						//모집 인원
	private int teamDeposit;						//예치금
	private LocalDate teamDate;				//팀 생성일
	
	@NotNull(message = "습관 시작일은 필수 선택사항 입니다. :)")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(message = "오늘 이전의 날짜는 선택 할 수 없습니다. :)")		// @FutureOrPresent : Now이거나 미래의 날짜, 시간이어야 한다.
	private LocalDate startDate;				//습관 시작일
	
	@NotNull(message = "습관 종료일은 필수 선택사항 입니다. :)")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(message = "오늘 이전의 날짜는 선택 할 수 없습니다. :)")
	private LocalDate endDate;					//습관 종료일
	
	@NotEmpty(message = "팀 소개 글은 필수 입력값 입니다. :)")
	private String teamIntroduce;				//팀 소개 글
	
	private String teamPhoto;					//팀 소개 사진
	private DeleteStatus teamDelete;		//팀 삭제 여부
}
