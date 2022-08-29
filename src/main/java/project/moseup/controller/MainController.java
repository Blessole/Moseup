package project.moseup.controller;

import java.util.Collection;
import java.util.Collections;
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
//		List<Team> topList = mainService.topList();
//		System.out.println("사이즈 = " + topList.size());
		
//		System.out.println("tno = " + topList.get(0).getTeamMembers().get(1).getTeam().getTno());
		
		List<Team> topList = mainService.topList();
		System.out.println("사이즈 = " + topList.get(0).getTno());
		System.out.println("사이즈 = " + topList.get(1).getTno());
		System.out.println("사이즈 = " + topList.get(2).getTno());
		System.out.println("사이즈 = " + topList.get(3).getTno());
		System.out.println("사이즈 = " + topList.get(4).getTno());
		
		//카테고리1 공부 인기순
		List<Team> studyTopList = mainService.studyTopList();
		//카테고리1 운동 인기순
		List<Team> exerciseTopList = mainService.exerciseTopList();	
		//카테고리1 습관 인기순
		List<Team> habitTopList = mainService.habitTopList();
		//카테고리1 ETC 인기순
		List<Team> etcTopList = mainService.etcTopList();
		//신규팀 24개
		List<Team> newTeamList = mainService.newTeamList();
		
		model.addAttribute("topList", topList);
		model.addAttribute("studyTopList", studyTopList);
		model.addAttribute("exerciseTopList", exerciseTopList);
		model.addAttribute("habitTopList", habitTopList);
		model.addAttribute("etcTopList", etcTopList);
		model.addAttribute("newTeamList", newTeamList);
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