package project.moseup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import project.moseup.service.TeamService;

@Controller
@RequiredArgsConstructor
public class TeamPageController {

	private final TeamService teamService;
	
	@GetMapping("teamPage")
	public String teamMainPage() {
		return "teams/teamMain";
	}
	
	@GetMapping("teamAskBoard")
	public String teamAskBoardPage() {
		return "teams/teamAskBoard";
	}
	
	@GetMapping("teamCheckBoard")
	public String teamCheckBoardPage() {
		return "teams/teamCheckBoard";
	}
	
	@GetMapping("teamAskBoard/teamAskBoardWriteForm")
	public String teamAskBoardWriteForm() {
		return "teams/askBoardWriteForm";
	}
}
