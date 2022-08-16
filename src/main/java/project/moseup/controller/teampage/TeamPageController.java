package project.moseup.controller.teampage;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.SecretStatus;
import project.moseup.domain.TeamAskBoard;
import project.moseup.domain.TeamAskBoardReply;
import project.moseup.dto.teamPage.TeamAskBoardDeleteDto;
import project.moseup.dto.teamPage.TeamAskBoardDetailDto;
import project.moseup.dto.teamPage.TeamAskBoardDto;
import project.moseup.dto.teamPage.TeamAskBoardReplyDto;
import project.moseup.dto.teamPage.TeamAskBoardUpdateDto;
import project.moseup.service.member.MemberService;
import project.moseup.service.teampage.TeamAskBoardReplyService;
import project.moseup.service.teampage.TeamAskBoardService;

@Controller
@RequiredArgsConstructor
@RequestMapping("teams")
public class TeamPageController {

	private final TeamAskBoardService teamAskBoardService;
	private final MemberService memberService;
	private final TeamAskBoardReplyService teamAskBoardReplyService;

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
	public String teamAskBoardPage(Model model, @PageableDefault(size = 10, sort = "tano", direction = Sort.Direction.DESC) Pageable pagable) {
		
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
        
		model.addAttribute("member", member);
		model.addAttribute("teamAsk", new TeamAskBoardDto());

		return "teams/askBoardWriteForm";
	}

	// 팀 페이지 문의 작성 결과
	@PostMapping("/teamAskBoard/teamAskBoardWriteForm/createTeamAsk")
	public String createTeamAsk(@Valid TeamAskBoardDto teamAsk, BindingResult result, Principal principal, @RequestParam(required = false) String secret) {

		Member member = this.memberService.getMember(principal.getName());
		
		teamAsk.setMember(member);
		
		if(secret != null) {
			teamAsk.setSecret(SecretStatus.SECRET);
		} else {
			teamAsk.setSecret(SecretStatus.PUBLIC);
		}
		
		teamAskBoardService.saveTeamAskBoard(teamAsk);

		return "redirect:/teams/teamAskBoard";
	}

	// 팀 페이지 문의 글 상세보기
	@GetMapping("/teamAskBoard/teamAskBoardDetail")
	public String teamAskBoardDetail(@RequestParam Long tano, Model model, Principal principal) {
		
		// 상세 보기 부분
		TeamAskBoard teamAskOne = teamAskBoardService.findOne(tano);
		TeamAskBoardDetailDto teamAskOneDetail = new TeamAskBoardDetailDto().toDto(teamAskOne);
		
		// 조회수 올리는 부분
		teamAskBoardService.increaseReadCount(tano);
		
		// 댓글 부분
		Member loginMember = this.memberService.getMember(principal.getName());		
			
		model.addAttribute("teamAskOne", teamAskOneDetail);
		model.addAttribute("loginMember", loginMember);	
		model.addAttribute("teamAskReply", new TeamAskBoardReplyDto());
		
		return "teams/teamAskBoardDetail";
	}
	
	// 댓글 작성
	@PostMapping("/teamAskBoard/teamAskBoardDetail/createTeamAskReply")
	public String createTeamAskBoardReply(@Valid TeamAskBoardReplyDto teamAskReply, BindingResult result, @RequestParam Long tano, Principal principal) {
		
		// 회원 정보 받아오기
		Member member = this.memberService.getMember(principal.getName());	
		teamAskReply.setMember(member);
		
		// 게시판 정보 받아오기
		TeamAskBoard teamAskBoard = teamAskBoardService.findOne(tano);
		teamAskReply.setTeamAskBoard(teamAskBoard);
		
		teamAskBoardReplyService.saveTeamAskBoardReply(teamAskReply);
		
		return "redirect:/teams/teamAskBoard/teamAskBoardDetail?tano=" + tano;
	}
	
	
	// 문의글 수정 폼
	@GetMapping("/teamAskBoard/updateForm")
	public String teamAskBoardUpdateForm(@RequestParam Long tano, Model model) {
		
		TeamAskBoard teamAskOne = teamAskBoardService.findOne(tano);
		TeamAskBoardDetailDto teamAskOneDetail = new TeamAskBoardDetailDto().toDto(teamAskOne);
		
		model.addAttribute("teamAskOne", teamAskOneDetail);
		
		return "teams/teamAskBoardUpdateForm";
	}
	
	// 문의글 수정 결과
	@PostMapping("/teamAskBoard/updateForm/update")
	public String teamAskBoardUpdate(@Valid TeamAskBoardUpdateDto teamAskOne, BindingResult result, @RequestParam(required = false) String secret, @RequestParam Long tano) {
		
		if(secret != null) {
			teamAskOne.setSecret(SecretStatus.SECRET);
		} else {
			teamAskOne.setSecret(SecretStatus.PUBLIC);
		}
		
		teamAskBoardService.changeUpdate(teamAskOne, tano);
		
		return "redirect:/teams/teamAskBoard/teamAskBoardDetail?tano=" + tano;
	}
	
	// 문의글 삭제 결과
	@GetMapping("/teamAskBoard/delete")
	public String teamAskBoardDelete(@Valid TeamAskBoardDeleteDto teamAskOne ,@RequestParam Long tano, Model model) {
		
		teamAskOne.setTeamAskDelete(DeleteStatus.TRUE);
		
		teamAskBoardService.changeDelete(teamAskOne, tano);
		
		return "redirect:/teams/teamAskBoard";
	}
	
	// 문의글 댓글

	// 팀 페이지 인증 게시판
	@GetMapping("/teamCheckBoard")
	public String teamCheckBoardPage() {
		return "teams/teamCheckBoard";
	}
}
