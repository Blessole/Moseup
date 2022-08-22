package project.moseup.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Team;
import project.moseup.service.MainService;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final MainService mainService;
	
	@RequestMapping("/")
	public String main(Model model) {
		List<Team> topList = mainService.topList();
		model.addAttribute("topList", topList);
		System.out.println("메인리스트 = " + topList);
		return "main";
	}
	
	
	
//	@RequestMapping(value = "*")
//	public String navbar(Model model, Principal principal) {
//		Member member = memberService.getMember(principal.getName());
//		System.out.println("멤버 = " + member);
//		model.addAttribute("navMember", member);
//		return "";
//	}
	 
}