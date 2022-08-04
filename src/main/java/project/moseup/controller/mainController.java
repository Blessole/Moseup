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
	
	@GetMapping("/search")	//검색
	public String teamSearch(@RequestParam(value = "keyword") String keyword, Model model) {
		List<Team> findAllList = teamService.findAll(keyword);
		if (findAllList.isEmpty()) {
		    model.addAttribute("findAllList", "nothing");
		} else {
		    model.addAttribute("findAllList", findAllList);
		}
		return "main/searchPage";
	}
	
//	@GetMapping("/search")	//팀검색
//	public String teamSearch(@RequestParam(value = "keyword") String keyword, Model model) {
//		List<Team> teamNmaeList = teamService.teamNameSearch(keyword);
//		List<Team> category1List = teamService.category1Search(keyword);
//		
//		if (teamNmaeList.isEmpty() || category1List.isEmpty()) {
//		    model.addAttribute("teamNmaeList", "nothing");
//		    model.addAttribute("category1List", "nothing");
//		    System.out.println(keyword);
//		} else {
//		    model.addAttribute("teamNmaeList", teamNmaeList);
//		    model.addAttribute("category1List", category1List);
//		    System.out.println(keyword);
//		    System.out.println("있음");
//		}
//		return "main/search";
//	}
}