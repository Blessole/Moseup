package project.moseup.controller;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import project.moseup.domain.TeamAskBoard;
import project.moseup.dto.TeamAskForm;
import project.moseup.service.TeamAskBoardService;
import project.moseup.service.TeamService;

@Controller
@RequiredArgsConstructor
@RequestMapping("teams")
public class TeamPageController {

	private final TeamService teamService;
	private final TeamAskBoardService teamaskBoardService;
	
	// 팀 페이지 메인
	@GetMapping("/teamPage")
	public String teamMainPage() {
		return "teams/teamMain";
	}
	
	// 팀 페이지 문의게시판
	@GetMapping("/teamAskBoard")
	public String teamAskBoardPage(Model model, Pageable pageable) {
		List<TeamAskBoard> teamAsks = teamaskBoardService.findTeamAsks();
		model.addAttribute("teamAsks", teamAsks);
		return "teams/teamAskBoard";
	}
	
	// 팀 페이지 문의 작성 폼
	@GetMapping("/teamAskBoard/teamAskBoardWriteForm")
	public String teamAskBoardWriteForm(Model model) {
		model.addAttribute("teamAsk", new TeamAskForm());
		
		return "teams/askBoardWriteForm";
	}
	
	// 팀 페이지 문의 작성 
	@PostMapping("/teamAskBoard/teamAskBoardWriteForm/createTeamAsk")
	public String createTeamAsk(TeamAskForm teamAsk) {
		
		TeamAskBoard teamAskBoard = new TeamAskBoard();
		LocalDate date = LocalDate.now();
		
		teamAskBoard.setTeamAskSubject(teamAsk.getTeamAskSubject());
		teamAskBoard.setTeamAskContent(teamAsk.getTeamAskContent());
		teamAskBoard.setTeamAskDate(date);
		
		teamaskBoardService.saveTeamAskBoard(teamAskBoard);
		
		return "redirect:/teamAskBoard";
	}
	
	// 팀 페이지 문의 글 상세보기
	@GetMapping("/teamAskBoard/TeamAskBoardDetail")
	public String teamAskBoardDetail(Long tano, Model model) {
		
		TeamAskBoard teamAskOne = teamaskBoardService.findOne(tano);
		
		model.addAttribute("teamAskOne", teamAskOne);
		
		return "teams/teamAskBoardDetail";
	}
	
	// 팀 페이지 인증 게시판
	@GetMapping("/teamCheckBoard")
	public String teamCheckBoardPage() {
		return "teams/teamCheckBoard";
	}
}
