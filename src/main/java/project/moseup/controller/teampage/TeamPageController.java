package project.moseup.controller.teampage;

import java.security.Principal;

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
import project.moseup.dto.teamPage.TeamAskBoardDto;
import project.moseup.dto.teamPage.TeamAskBoardUpdateDto;
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
	public String teamAskBoardDetail(@RequestParam Long tano, Model model) {

		TeamAskBoard teamAskOne = teamAskBoardService.findOne(tano);

		Member member = teamAskOne.getMember();

		model.addAttribute("teamAskOne", teamAskOne);
		model.addAttribute("findMember", member);

		return "teams/teamAskBoardDetail";
	}

	// 문의글 수정 폼
	@GetMapping("/teamAskBoard/updateForm")
	public String teamAskBoardUpdateForm(@RequestParam Long tano, Model model) {

		TeamAskBoard teamAskOne = teamAskBoardService.findOne(tano);

		Member member = teamAskOne.getMember();

		TeamAskBoardUpdateDto updateDto = new TeamAskBoardUpdateDto();

		model.addAttribute("teamAskOne", teamAskOne);
		model.addAttribute("findMember", member);
		model.addAttribute("updateDto", updateDto);

		return "teams/teamAskBoardUpdateForm";
	}

	// 문의글 수정 결과
	@PostMapping("/teamAskBoard/updateForm/update")
	public String teamAskBoardUpdate(TeamAskBoardUpdateDto updateDto, @RequestParam(required = false) String secret, @RequestParam Long tano) {

//		TeamAskBoard teamAskOneReal = teamAskBoardService.findOne(tano);
		System.out.println("updateDto 내용 : "+updateDto.getTeamAskContent());
		System.out.println("updateDto 제목 : "+updateDto.getTeamAskSubject());
		System.out.println("updateDto 비밀글여부 : "+updateDto.getSecret());

		if(secret != null) {
			updateDto.setSecret(SecretStatus.SECRET);
		} else {
			updateDto.setSecret(SecretStatus.PUBLIC);
		}

		teamAskBoardService.changeUpdate(updateDto, tano);

		return "redirect:/teams/teamAskBoard/teamAskBoardDetail?tano="+tano;
	}

	// 문의글 삭제
	@GetMapping("/teamAskBoard/delete")
	public String teamAskBoardDelete(@RequestParam Long tano, Model model) {

		TeamAskBoard teamAskOne = teamAskBoardService.findOne(tano);

		teamAskOne.setTeamAskDelete(DeleteStatus.TRUE);

		teamAskBoardService.changeDelete(teamAskOne);

		return "redirect:/teams/teamAskBoard";
	}

	// 팀 페이지 인증 게시판
	@GetMapping("/teamCheckBoard")
	public String teamCheckBoardPage() {
		return "teams/teamCheckBoard";
	}
}
