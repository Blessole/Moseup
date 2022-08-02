package project.moseup.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Team;
import project.moseup.service.TeamService;

@Controller
@RequiredArgsConstructor
public class mainController {
	
	private final TeamService teamService;

	@RequestMapping("/")
	public String main() {
		return "main";
	}
	
	@GetMapping("/search")	//팀검색
	public String teamSearch(@RequestParam(value = "keyword") String keyword, Model model) {
		List<Team> teamSearchList = teamService.teamSearch(keyword);
		
		if (teamSearchList.isEmpty()) {
		    model.addAttribute("teamSearchList", "nothing");
		} else {
		    model.addAttribute("teamSearchList", teamSearchList);
		}
		return "main/search";
	}
}