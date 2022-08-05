package project.moseup.controller.teampage;

import java.security.Principal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Member;
import project.moseup.domain.TeamAskBoard;
import project.moseup.dto.TeamAskForm;
import project.moseup.service.TeamAskBoardService;
import project.moseup.service.member.MemberService;

@Controller
@RequiredArgsConstructor
@RequestMapping("teams")
public class TeamPageController {

	private final TeamAskBoardService teamAskBoardService;
	private final MemberService memberService;

	// 팀 페이지 메인
	@GetMapping("/teamPage")
	public String teamMainPage() {
		return "teams/teamMain";
	}

	/*
	 * // 팀 페이지 문의게시판(전체 보여주기)
	 * 
	 * @GetMapping("/teamAskBoard") public String teamAskBoardPage(Model model) {
	 * List<TeamAskBoard> teamAsks = teamAskBoardService.findTeamAsks();
	 * model.addAttribute("teamAsks", teamAsks);
	 * 
	 * return "teams/teamAskBoard"; }
	 */

	// 팀 페이지 문의게시판(페이징)
	@GetMapping("/teamAskBoard")
	public String teamAskBoardPage(Model model, @PageableDefault(size = 5, sort = "tano", direction = Sort.Direction.DESC) Pageable pagable) {
		
		Page<TeamAskBoard> teamAsks = teamAskBoardService.findTeamAsksPage(pagable);
		int startPage = Math.max(1, teamAsks.getPageable().getPageNumber() - 4);
		int endPage = Math.min(teamAsks.getTotalPages(), teamAsks.getPageable().getPageNumber() + 5);
		
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("teamAsks", teamAsks);

		return "teams/teamAskBoard";
	}

	// 팀 페이지 문의 작성 폼
	@GetMapping("/teamAskBoard/teamAskBoardWriteForm")
	public String teamAskBoardWriteForm(Model model, Principal principal) {
		Member member = this.memberService.getMember(principal.getName());
		System.out.println("member : " + member);
		model.addAttribute("member", member);
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

		teamAskBoardService.saveTeamAskBoard(teamAskBoard);

		return "redirect:/teams/teamAskBoard";
	}

	// 팀 페이지 문의 글 상세보기
	@GetMapping("/teamAskBoard/TeamAskBoardDetail")
	public String teamAskBoardDetail(Long tano, Model model) {

		TeamAskBoard teamAskOne = teamAskBoardService.findOne(tano);

		model.addAttribute("teamAskOne", teamAskOne);

		return "teams/teamAskBoardDetail";
	}

	// 팀 페이지 인증 게시판
	@GetMapping("/teamCheckBoard")
	public String teamCheckBoardPage() {
		return "teams/teamCheckBoard";
	}
}
