package project.moseup.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Team;
import project.moseup.service.SearchService;
import project.moseup.service.TeamService;

@Controller
@RequiredArgsConstructor
public class SearchController {
	
	private final SearchService searchService;
	private final TeamService teamService;
	
	@GetMapping("/search")	//검색
	public String teamSearch(@RequestParam(value = "keyword") String keyword, Model model) {
		List<Team> searchedTeamList = searchService.findKeywordAll(keyword);
		
		if (searchedTeamList.isEmpty()) {
		    model.addAttribute("searchedTeamList", "nothing");
		    model.addAttribute("keyword", keyword);	//검색 후 검색창에 보여주기 위함
		} else {
		    model.addAttribute("searchedTeamList", searchedTeamList);
		    model.addAttribute("keyword", keyword);
		}
		return "main/searchPage";
	}

}
