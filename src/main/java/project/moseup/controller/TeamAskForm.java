package project.moseup.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TeamAskForm {

	private Long tano;
	private String nickname;
	
	private String teamAskSubject;
	private String teamAskContent;
}
