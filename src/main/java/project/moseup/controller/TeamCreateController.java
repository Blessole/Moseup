package project.moseup.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.dto.TeamForm;
import project.moseup.service.TeamService;
import project.moseup.service.member.MemberService;

@Controller
@RequiredArgsConstructor
public class TeamCreateController {

	private final TeamService teamService;
	private final MemberService memberService;

	@GetMapping("/teams/createTeam")
	public String createTeamFrom(Model model) {
		model.addAttribute("teamForm", new TeamForm());
		return "teamCreateForm";
	}

	//	team 생성
	@PostMapping("/teams/createTeam")
	public String  createTeam(@Valid TeamForm teamForm, BindingResult result, Principal principal) {
		//@Valid : 클라이언트 측에서 넘어온 데이터를 객체에 바인딩(속성과 개체 사이 또는 연산과 기호사이와 같은 연관)할 때 유효성 검사함
		//@Valid다음에 BindingResult가 있으면 result에 오류가 담긴후 코드가 실행됨.
		if (result.hasErrors()) {	// result안에 에러가 있으면
			return "teamCreateForm"; // 에러를 createMemberForm으로 가져감
		}

		//principal을 통해서 로그인한 멤버 생성
		Member member = memberService.getMember(principal.getName());
		Member findNickname = memberService.findOne(member.getMno());
		
		teamForm.setMember(member);
		teamForm.setTeamLeader(findNickname.getNickname());
		teamService.create(teamForm);

		return "redirect:/";	// 초기화면으로 돌아감
	}

	// 팀명 중복체크
	@GetMapping(value = "/teams/nameChk", produces = "text/html;charset=utf-8")
	@ResponseBody	
	public String teamNameChk(String teamName) {
	      String msg = "";
	      List<Team> team = teamService.validateDuplicateTeam(teamName);
	      if(team == null) msg = "사용 가능한 팀명입니다. :)";
	      else msg = "중복된 팀명입니다. :(";
	      return msg;
	   }

}
