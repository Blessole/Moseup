package project.moseup.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.MemberGender;
import project.moseup.domain.TeamAskBoard;
import project.moseup.service.TeamAskBoardService;
import project.moseup.service.TeamService;

@Controller
@RequiredArgsConstructor
public class TeamPageController {

	private final TeamService teamService;
	private final TeamAskBoardService teamaskBoardService;
	
	@GetMapping("teamPage")
	public String teamMainPage() {
		return "teams/teamMain";
	}
	
	@GetMapping("teamAskBoard")
	public String teamAskBoardPage(Member member) {
		return "teams/teamAskBoard";
	}
	
	@GetMapping("teamCheckBoard")
	public String teamCheckBoardPage() {
		return "teams/teamCheckBoard";
	}
	
	@GetMapping("teamAskBoard/teamAskBoardWriteForm")
	public String teamAskBoardWriteForm(Model model) {
		model.addAttribute("teamAsk", new TeamAskForm());
		
		return "teams/askBoardWriteForm";
	}
	
	@PostMapping("teamAskBoard/teamAskBoardWriteForm/createTeamAsk")
	public String createTeamAsk(TeamAskForm teamAsk) {
		
		Member member = new Member();
		LocalDateTime date = LocalDateTime.now();
		member.setEmail("가글");
		member.setPassword("네이년");
		member.setNickname("널");
		member.setName("널");
		member.setGender(MemberGender.MALE);
		member.setAddress("서울");
		member.setPhone("010");
		member.setMemberDelete(DeleteStatus.FALSE);
		member.setMemberDate(date);
		
		TeamAskBoard teamAskBoard = new TeamAskBoard();
		
		teamAskBoard.setTeamAskSubject(teamAsk.getTeamAskSubject());
		teamAskBoard.setTeamAskContent(teamAsk.getTeamAskContent());
		
		teamaskBoardService.saveTeamAskBoard(teamAskBoard);
		
		return "teams/teamAskBoardWriteResult";
	}
}
