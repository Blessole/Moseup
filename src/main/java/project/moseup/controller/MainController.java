package project.moseup.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.exception.NoLoginException;
import project.moseup.service.MainService;
import project.moseup.service.admin.AdminMemberService;
import project.moseup.service.member.MemberService;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final MainService mainService;
	private final MemberService memberService;
	private final AdminMemberService adminMemberService;

	// 공용 데이터 (네비바에 들어갈 회원 정보)
	@ModelAttribute
	public void loginMember(Principal principal, Model model){
		if(principal == null){
//			throw new NoLoginException();
		}else{
			Member member = memberService.getPrincipal(principal);
			Map<String, Object> memberMap = adminMemberService.getMemberMap(member.getMno());

			model.addAttribute("memberMap", memberMap);
		}
	}

	@RequestMapping("/")
	public String main(Model model) { //메소드명 정정

		//best5 습관
		List<Team> topList = mainService.topList();
		//카테고리1 공부 최신순
		List<Team> studyTopList = mainService.studyTopList();
		//카테고리1 운동 최신순
		List<Team> exerciseTopList = mainService.exerciseTopList();
		//카테고리1 습관 최신순
		List<Team> habitTopList = mainService.habitTopList();
		//카테고리1 ETC 최신순
		List<Team> etcTopList = mainService.etcTopList();
		//신규팀 24개
		List<Team> newTeamList = mainService.newTeamList();
		
		LocalDate today = LocalDate.now();
		model.addAttribute("today", today);
		model.addAttribute("topList", topList);
		model.addAttribute("studyTopList", studyTopList);
		model.addAttribute("exerciseTopList", exerciseTopList);
		model.addAttribute("habitTopList", habitTopList);
		model.addAttribute("etcTopList", etcTopList);
		model.addAttribute("newTeamList", newTeamList);

		return "main";
	}
}