package project.moseup.controller;

import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.service.SearchService;
import project.moseup.service.admin.AdminMemberService;
import project.moseup.service.member.MemberService;

@Controller
@RequiredArgsConstructor
@RequestMapping("search")
public class SearchController {
	
	private final SearchService searchService;
	private final MemberService memberService;
	private final AdminMemberService adminMemberService;

	// 공용 데이터 (네비바에 들어갈 회원 정보)
	@ModelAttribute
	public void loginMember(Principal principal, Model model){
		if(principal == null){
//				throw new NoLoginException();
		}else{
			Member member = memberService.getPrincipal(principal);
			Map<String, Object> memberMap = adminMemberService.getMemberMap(member.getMno());

			model.addAttribute("memberMap", memberMap);
		}
	}

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

		LocalDate today = LocalDate.now();
		model.addAttribute("today", today);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("searchedTeamList", searchedTeamList);
		model.addAttribute("keyword", keyword);
		model.addAttribute("filter1", filter1);
		model.addAttribute("filter2", filter2);

		return "search/searchPage";
	}
}
