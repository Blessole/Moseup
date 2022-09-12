package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.exception.NoLoginException;
import project.moseup.service.MainService;
import project.moseup.service.admin.AdminMemberService;
import project.moseup.service.member.MemberService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final MainService mainService;
	private final MemberService memberService;
	private final AdminMemberService adminMemberService;

	// 공용 데이터 (사이드바에 들어갈 회원 정보)
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
//		List<Team> topList = mainService.topList();
//		System.out.println("사이즈 = " + topList.size());
		
//		System.out.println("tno = " + topList.get(0).getTeamMembers().get(1).getTeam().getTno());

		List<Team> topList = mainService.topList();

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
//		System.out.println("메인리스트 = " + topList);
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