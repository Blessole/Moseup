package project.moseup.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Team;
import project.moseup.service.SearchService;

@Controller
@RequiredArgsConstructor
@RequestMapping("search")
public class SearchController {
	
	private final SearchService searchService;
	
	//검색 페이징
	@GetMapping("")
	public String filter2(@RequestParam(required = false, defaultValue = "") String keyword, String filter1,
			String filter2, Model model, @PageableDefault(size = 12) Pageable pageable) {

		System.out.println("키워드 = " + keyword);
		System.out.println("필터1 = " + filter1);
		System.out.println("필터2 = " + filter2);

		Page<Team> searchedTeamList = searchService.searchedFilterList(keyword, filter1, filter2, pageable);
		
		int startPage = Math.max(1, searchedTeamList.getPageable().getPageNumber() - 5);
		int endPage = Math.min(searchedTeamList.getTotalPages(), searchedTeamList.getPageable().getPageNumber() + 5);

		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("searchedTeamList", searchedTeamList);
		model.addAttribute("keyword", keyword);
		model.addAttribute("filter1", filter1);
		model.addAttribute("filter2", filter2);

		return "search/searchPage";
	}

}
