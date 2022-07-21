package project.moseup.controller;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class teamForm {
//	private Member member;	// 팀장이름임
	private String teamName;
	private int teamVolume;
	private int teamDeposit;
	private LocalDateTime teamDate;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String teamIntroduce;
	private String teamPhoto;
}
