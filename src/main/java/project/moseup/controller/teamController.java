package project.moseup.controller;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.service.MemberService;
import project.moseup.service.TeamService;

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
	
	@PostMapping("/teams/createTeam")
	public String  createTeam(@Valid TeamForm teamForm, BindingResult result) {
		//@Valid : 클라이언트 측에서 넘어온 데이터를 객체에 바인딩(속성과 개체 사이 또는 연산과 기호사이와 같은 연관)할 때 유효성 검사함
		//@Valid다음에 BindingResult가 있으면 result에 오류가 담긴후 코드가 실행됨.
		
		if (result.hasErrors()) {	// result안에 에러가 있으면
			return "teams/createTeamForm"; // 에러를 createMemberForm으로 가져감
		}
		
		Team team = new Team();
		Member member = new Member();
		// 세션을 통해서 멤버 가져와야됨(나중에 작성)
		
		//임시 멤버(나중에 삭제)
		Member member1 = memberService.findOne((long) 1);
		
		team.setMember(member1);
		team.setTeamName(teamForm.getTeamName());
		team.setTeamVolume(teamForm.getTeamVolume());
		team.setTeamDeposit(teamForm.getTeamDeposit());
		team.setTeamDate(LocalDate.now());
		team.setStartDate(teamForm.getStartDate());
		team.setEndDate(teamForm.getEndDate());
		team.setTeamIntroduce(teamForm.getTeamIntroduce());
		if (teamForm.getTeamPhoto() != null && !teamForm.getTeamPhoto().equals("")) {
			team.setTeamPhoto(teamForm.getTeamPhoto());
		}
		team.setTeamDelete(DeleteStatus.FALSE);
		
		teamService.create(team);
		
		return "redirect:/";	// 초기화면으로 돌아감
	}
}
