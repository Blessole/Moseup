package project.moseup.controller;

import java.security.Principal;
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
	public String teamSearch(@RequestParam(value = "keyword") String keyword, Model model, Principal principal) {
		List<Team> findAllList = teamService.findAll(keyword);
		
		if (findAllList.isEmpty()) {
		    model.addAttribute("findAllList", "nothing");
		    model.addAttribute("keyword", keyword);	//검색 후 검색창에 보여주기 위함
		} else {
		    model.addAttribute("findAllList", findAllList);
		    model.addAttribute("keyword", keyword);
		}
		return "main/searchPage";
	}
}