package project.moseup.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import groovyjarjarantlr4.v4.parse.ANTLRParser.finallyClause_return;
import lombok.RequiredArgsConstructor;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.dto.TeamForm;
import project.moseup.service.TeamService;
import project.moseup.service.member.MemberService;

@Controller
@RequiredArgsConstructor
public class teamController {

	private final TeamService teamService;
	private final MemberService memberService;

	@GetMapping("/teams/createTeam")
	public String createTeamFrom(Model model) {
		model.addAttribute("teamForm", new TeamForm());
		return "teams/createTeamForm";
	}

	//	team 생성
	@PostMapping("/teams/createTeam")
	public String  createTeam(@Valid TeamForm teamForm, BindingResult result, Principal principal) {
		//@Valid : 클라이언트 측에서 넘어온 데이터를 객체에 바인딩(속성과 개체 사이 또는 연산과 기호사이와 같은 연관)할 때 유효성 검사함
		//@Valid다음에 BindingResult가 있으면 result에 오류가 담긴후 코드가 실행됨.
		System.out.println("팀명 : "+teamForm.getTeamName());
		
		System.out.println(teamForm.getStartDate());
		System.out.println(teamForm.getEndDate());
		System.out.println(teamForm.getTeamCategory1());
		System.out.println(teamForm.getTeamDeposit());
		System.out.println(teamForm.getTeamVolume());

		if (result.hasErrors()) {	// result안에 에러가 있으면
			return "teams/createTeamForm"; // 에러를 createMemberForm으로 가져감
		}

		//principal을 통해서 로그인한 멤버 생성
		Member member = memberService.getMember(principal.getName());
		
		teamForm.setMember(member);
		System.out.println(teamForm.getMember());
		teamService.create(teamForm);
		
		
//		team.setMember(member);
//		team.setTeamName(teamForm.getTeamName());
//		team.setTeamVolume(teamForm.getTeamVolume());
//		team.setTeamCategory1(teamForm.getTeamCategory1());
//		team.setTeamCategory2(teamForm.getTeamCategory2());
//		team.setTeamCategory3(teamForm.getTeamCategory3());
//		team.setTeamDeposit(teamForm.getTeamDeposit());
//		team.setTeamDate(LocalDate.now());
//		team.setStartDate(teamForm.getStartDate());
//		team.setEndDate(teamForm.getEndDate());
//		team.setTeamIntroduce(teamForm.getTeamIntroduce());
//		if (teamForm.getTeamPhoto() != null && !teamForm.getTeamPhoto().equals("")) {
//			team.setTeamPhoto(teamForm.getTeamPhoto());
//		}
//		team.setTeamDelete(DeleteStatus.FALSE);

//		teamService.create(team);

		return "redirect:/";	// 초기화면으로 돌아감
	}

	// 팀명 중복체크
	@GetMapping(value = "/teams/nameChk", produces = "text/html;charset=utf-8")
	@ResponseBody	// 전에는 return "idChk";통해 보여주지만, @ResponseBody는 jsp를 통하지 않고 직접 문자를 전달함
	public String teamNameChk(String teamName) {
	      String msg = "";
	      List<Team> team = teamService.validateDuplicateTeam(teamName);
	      if(team == null) msg = "사용 가능한 팀명입니다. :)";
	      else msg = "중복된 팀명입니다. :(";
	      return msg;
	   }

}
