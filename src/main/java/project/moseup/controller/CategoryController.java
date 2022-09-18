package project.moseup.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.service.CategoryService;
import project.moseup.service.admin.AdminMemberService;
import project.moseup.service.member.MemberService;

@Controller
@RequiredArgsConstructor
public class CategoryController {
	
	private final CategoryService categoryService;
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
	
	@GetMapping("/category")
	public String categoryPage(String keyword, Model model, String filter2, String deposit, String depositCheck, 
			String volume, String volumeCheck, String notInclud, @PageableDefault(size = 12) Pageable pageable) {
		
		if (deposit == "") deposit = null;
		if (volume == "") volume = null;
		if (notInclud == "") notInclud = null;
		if (filter2 == "") filter2 = null;
		
		Page<Team> categoryList = categoryService.searchedFilterList(keyword, deposit, volume, notInclud, filter2, pageable);
		
		//best3 습관(찜 많은 순으로 바꿔야함)
		List<Team> topList = categoryService.topList(keyword);
		
		
		int startPage = Math.max(1, categoryList.getPageable().getPageNumber() - 5);
		int endPage = Math.min(categoryList.getTotalPages(), categoryList.getPageable().getPageNumber() + 5);
		
		LocalDate today = LocalDate.now();
		model.addAttribute("today", today);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("topList", topList);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("keyword", keyword);
		model.addAttribute("deposit", deposit);
		model.addAttribute("volume", volume);
		model.addAttribute("notInclud", notInclud);
		model.addAttribute("filter2", filter2);
		model.addAttribute("depositCheck", depositCheck);
		model.addAttribute("volumeCheck", volumeCheck);
		
		return "category/categoryPage";
	}
}