package project.moseup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import project.moseup.service.TeamService;

@Controller
@RequiredArgsConstructor
public class teamController {
	
	private final TeamService teamService;

	@GetMapping("/teams/createTeam")
	public String createTeamFrom(Model model) {
		model.addAttribute("teamForm", new teamForm());
		return "teams/createTeamForm";
	}
}
